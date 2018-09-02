package sp.circuit

import diode._
import diode.react.ReactConnector
import org.scalajs.dom.ext.LocalStorage
import sp.SwitchTheme
import sp.dashboard.{AbstractDashboardPresetsHandler, Dashboard}
import sp.theming.SPStyleSheet.Theme

object SPGUICircuit extends Circuit[SPGUIModel] with ReactConnector[SPGUIModel] {
  def initialModel = BrowserStorage.load.getOrElse(InitialState())
  val actionHandler = composeHandlers(
    new PresetsHandler(
      zoomRW(m => PresetsHandlerScope(m.presets, m.openWidgets, m.widgetData))
      ((m, phs) => m.copy(presets = phs.presets, openWidgets = phs.openWidgets, widgetData = phs.widgetData))
    ),
    new OpenWidgetsHandler(
      zoomRW(_.openWidgets)((m, v) => m.copy(openWidgets = v))
    ),
    new GlobalStateHandler(
      zoomRW(_.globalState)((m, v) => m.copy(globalState = v))
    ),
    new SettingsHandler(
      zoomRW(_.settings)((m, v) => m.copy(settings = v))
    ),
    new WidgetDataHandler(
      zoomRW(_.widgetData)((m,v) => m.copy(widgetData = v))
    ),
    new DraggingHandler(
      zoomRW(_.draggingState)((m,v) => m.copy(draggingState = v))
    ),
    new ModalHandler(
      zoomRW(_.modalState)((m,v) => m.copy(modalState = v))
    )
  )
  // store state upon any model change
  subscribe(zoomRW(myM => myM)((m,v) => v))(m => BrowserStorage.store(m.value))

  var dashboardPresetHandler: Option[AbstractDashboardPresetsHandler] = None
}

case class PresetsHandlerScope(presets: DashboardPresets, openWidgets: OpenWidgets, widgetData: WidgetData)

class PresetsHandler[M](modelRW: ModelRW[M, PresetsHandlerScope]) extends ActionHandler(modelRW) {

  override def handle = {
    case AddDashboardPreset(name) => { // Takes current state of dashboard and saves in list of presets
      val newPreset = DashboardPreset(
        value.openWidgets,
        WidgetData(value.widgetData.xs.filterKeys(value.openWidgets.xs.keySet.contains(_)))
      )

      // Tell persistent storage to add preset
      SPGUICircuit.dashboardPresetHandler.flatMap(h => {h.storePresetToPersistentStorage(name, newPreset);None})

      updated(value.copy(presets = DashboardPresets(value.presets.xs + (name -> newPreset))))
    }

    case RemoveDashboardPreset(name) => { // Removes the preset corresponding to the given name

      // Tell persistent storage to remove preset
      SPGUICircuit.dashboardPresetHandler.flatMap(h => {h.removePresetFromPersistentStorage(name);None})

      updated(value.copy(presets = DashboardPresets(value.presets.xs - name)))
    }

    case SetDashboardPresets(presets: Map[String, DashboardPreset]) => {
      updated(value.copy(presets = DashboardPresets(presets)))
    }

    case RecallDashboardPreset(preset) => {
      updated(value.copy(openWidgets = OpenWidgets())) //First remove all widgets to let them unmount
      updated(value.copy(openWidgets = preset.widgets, widgetData = preset.widgetData))
    }
  }
}

class OpenWidgetsHandler[M](modelRW: ModelRW[M, OpenWidgets]) extends ActionHandler(modelRW) {
  private def combinations[A, B](as: Seq[A], bs: Seq[B]) = for (a <- as; b <- bs) yield (a, b)

  override def handle = {
    case AddWidget(widgetType, width, height, id) =>
      println("AddWidget")
      val occupiedGrids = value.xs.values.flatMap { w =>
        val xs = w.layout.x until w.layout.w + w.layout.x
        val ys = w.layout.y until w.layout.h + w.layout.y

        combinations(xs, ys)
      }

      val bestPosition = Stream.from(0).find(i => {
        val x = i % Dashboard.cols
        val y = i / Dashboard.cols

        val xs = x until x + width
        val ys = y until y + height
        val requiredGrids = combinations(xs, ys)

        requiredGrids.forall(req =>
          occupiedGrids.forall(occ =>
            !(occ._1 == req._1 && occ._2 == req._2 || req._1 >= Dashboard.cols)
          )
        )
      }).get

      val x = bestPosition % Dashboard.cols
      val y = bestPosition / Dashboard.cols

      val newWidget = OpenWidget(id, WidgetLayout(x, y, width, height), widgetType)
      println(s"Widget created: $newWidget")

      updated(OpenWidgets(value.xs + (id -> newWidget)))
    case CloseWidget(id) =>
      updated(OpenWidgets(value.xs - id))
    case CollapseWidgetToggle(id) =>
      val targetWidget = value.xs(id)
      val modifiedWidget = targetWidget.layout.h match {
        case 1 => targetWidget.copy(
          layout = targetWidget.layout.copy(
            collapsedHeight = 1,
            h = targetWidget.layout.collapsedHeight match {
              // this deals with the fact that panels can already have a height of 1
              // it would be strange to "restore" the height to the current height
              case 1 => 4
              case _ => targetWidget.layout.collapsedHeight
            }
          )
        )
        case _ => targetWidget.copy(
          layout = targetWidget.layout.copy(
            collapsedHeight = targetWidget.layout.h ,
            h = 1
          )
        )
      }
      updated(OpenWidgets((value.xs - id ) + (id -> modifiedWidget)))
    case CloseAllWidgets => updated(OpenWidgets())
    case UpdateLayout(id, newLayout) =>
      val widgets = value.xs.get(id)
        .map(_.copy(layout = newLayout))
        .map(x => value.xs + (x.id -> x))
        .getOrElse(value.xs)

      updated(OpenWidgets(widgets))

    case SetLayout(newLayout) =>
      println("Inc:")
      println(newLayout)
      println("SetLayout(newLayout)")
      val updatedWidgets = value.xs.flatMap { case (_, widget) =>
        newLayout.get(widget.id).map(l => widget.copy(layout = l))
      }

      println("Updated:")
      println(updatedWidgets)

      updated(OpenWidgets(updatedWidgets.map(w => w.id -> w).toMap))
    case LayoutsChanged(layouts) =>
      val widgets = value.xs
      widgets.foreach(x => println(x._2.layout.y))
      val updatedWidgets = widgets.map { case (id, widget) =>
        val updatedWidget = layouts.find(_.key == id.toString).map(l => widget.copy(
          layout = widget.layout.copy(x = l.x, y = l.y, w = l.w, h = l.h)
        )).getOrElse(widget)

        id -> updatedWidget
      }

      updated(OpenWidgets(updatedWidgets))
  }
}

class GlobalStateHandler[M](modelRW: ModelRW[M, GlobalState]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateGlobalState(state) =>
      updated(state)
    case UpdateGlobalAttributes(key, v) =>
      val attr = value.attributes + (key->v)
      updated(value.copy(attributes = attr))
  }
}

class WidgetDataHandler[M](modelRW: ModelRW[M, WidgetData]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateWidgetData(id, d) =>
      val updW = value.xs + (id -> d)
      updated(WidgetData(updW))
  }
}

class SettingsHandler[M](modelRW: ModelRW[M, Settings]) extends ActionHandler(modelRW) {
  override def handle = {
    case SetTheme(newTheme) => {
      SwitchTheme.to(newTheme.name)
      updated(value.copy(
        theme = newTheme
      ))
    }
    case ToggleHeaders => {
      updated(value.copy(
        showHeaders = !value.showHeaders
      ))
    }
  }
}

class DraggingHandler[M](modelRW: ModelRW[M, DraggingState]) extends ActionHandler(modelRW) {
  override def handle = {
    case SetDraggableRenderStyle(renderStyle) => updated(value.copy(renderStyle = renderStyle))
    case SetDraggableData(data) => updated(value.copy(data = data))
    case SetCurrentlyDragging(dragging) => updated((value.copy(dragging = dragging)))
    case SetDraggingTarget(id) => updated((value.copy(target = Some(id))))
    case UnsetDraggingTarget => updated((value.copy(target = None)))
    case DropEvent(struct, target) =>
      updated((value.copy(latestDropEvent = Some(DropEventData(struct, target)))))
  }
}

class ModalHandler[M](modelRW: ModelRW[M, ModalState]) extends ActionHandler(modelRW) {
  override def handle = {
    case OpenModal(title, component, onComplete) => {
      updated(value.copy(
        modalVisible = true,
        title = title,
        component = Some(component),
        onComplete = Some(onComplete)
      ))
    }

    case CloseModal => {
      updated(value.copy(modalVisible = false, component = None, onComplete = None))
    }
  }
}

object BrowserStorage {
  import sp.domain._
  import sp.domain.Logic._
  import JsonifyUIState._
  val namespace = "SPGUIState"

  def store(spGUIState: SPGUIModel) = LocalStorage(namespace) = SPValue(spGUIState).toJson
  def load: Option[SPGUIModel] =
    LocalStorage(namespace).flatMap(x => SPAttributes.fromJsonGetAs[SPGUIModel](x))
}

object JsonifyUIState {
  import sp.domain._
  import Logic._
  import play.api.libs.json._

  implicit val fTheme: JSFormat[Theme] = Json.format[Theme]
  implicit val fSettings: JSFormat[Settings] = Json.format[Settings]
  implicit val fWidgetData: JSFormat[WidgetData] = Json.format[WidgetData]
  implicit val fWidgetLayout: JSFormat[WidgetLayout] = Json.format[WidgetLayout]
  implicit val fOpenWidget: JSFormat[OpenWidget] = Json.format[OpenWidget]
  implicit val fDropEvent: JSFormat[DropEventData] = Json.format[DropEventData]
  implicit val fDraggingState: JSFormat[DraggingState] = Json.format[DraggingState]

  implicit lazy val modalStateWrites: JSWrites[ModalState] =
    new OWrites[ModalState] {
      override def writes(o: ModalState): SPAttributes =
        JsObject(Map("nothing" -> SPValue.empty)) // we don't care
    }

  implicit lazy val modalStateReads: JSReads[ModalState] =
    new JSReads[ModalState] {
      override def reads(json: SPValue): JsResult[ModalState] = {
        JsSuccess(ModalState()) // we actually don't care about parsing modalState so we just instantiate a new one
      }
    }

  implicit lazy val dashboardPresetsMapReads: JSReads[Map[String, DashboardPreset]] =
    new JSReads[Map[String, DashboardPreset]] {
      override def reads(json: JsValue): JsResult[Map[String, DashboardPreset]] = {
        json.validate[Map[String, SPValue]].map{xs =>
          def isCorrect(k: String, v: SPValue) = v.to[DashboardPreset].isSuccess
          xs.collect{case (k, v) if isCorrect(k, v) => k -> v.to[DashboardPreset].get}
        }
      }
    }
  implicit lazy val dashboardPresetsMapWrites: JSWrites[Map[String, DashboardPreset]] =
    new OWrites[Map[String, DashboardPreset]] {
      override def writes(xs: Map[String, DashboardPreset]): JsObject = {
        val toFixedMap = xs.map{case (k, v) => k -> SPValue(v)}
        JsObject(toFixedMap)
      }
    }

  implicit lazy val openWidgetMapReads: JSReads[Map[ID, OpenWidget]] = new JSReads[Map[ID, OpenWidget]] {
    override def reads(json: JsValue): JsResult[Map[ID, OpenWidget]] = {
      json.validate[Map[String, SPValue]].map{xs =>
        def isCorrect(k: String, v: SPValue) = ID.isID(k) && v.to[OpenWidget].isSuccess
        xs.collect{case (k, v) if isCorrect(k, v) => ID.makeID(k).get -> v.to[OpenWidget].get}}
    }
  }
  implicit lazy val openWidgetMapWrites: JSWrites[Map[ID, OpenWidget]] = new OWrites[Map[ID, OpenWidget]] {
    override def writes(xs: Map[ID, OpenWidget]): JsObject = {
      val toFixedMap = xs.map{case (k, v) => k.toString -> SPValue(v)}
      JsObject(toFixedMap)
    }
  }
  implicit val fOpenWidgets: JSFormat[OpenWidgets] = Json.format[OpenWidgets]
  implicit val fDashboardPreset: JSFormat[DashboardPreset] = Json.format[DashboardPreset]
  implicit val fDashboardPresets: JSFormat[DashboardPresets] = Json.format[DashboardPresets]
  implicit val fGlobalState: JSFormat[GlobalState] = Json.format[GlobalState]
  implicit val fSPGUIModel: JSFormat[SPGUIModel] = Json.format[SPGUIModel]
}
