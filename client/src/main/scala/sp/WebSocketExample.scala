package sp

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import play.api.libs.json.Json
import sp.shared.DataAPI._
import sp.shared.Pipeline

object WebSocketExample {
  import org.scalajs.dom.ext.KeyCode
  import org.scalajs.dom.{CloseEvent, Event, MessageEvent, WebSocket}

  import scala.scalajs.js

  val url = "ws://localhost:9000/data/test"

  case class State(ws: Option[WebSocket], logLines: Vector[String], message: String) {

    def allowSend: Boolean =
      ws.exists(_.readyState == WebSocket.OPEN) && message.nonEmpty

    // Create a new state with a line added to the log
    def log(line: String): State =
      copy(logLines = logLines :+ line)
  }

  class Backend($: BackendScope[Unit, State]) {
    def render(s: State) = {
      def p1 = Pipeline[Int] ~> MultiplyBy(4) ~> ToString ~> DuplicateString
      def p2 = Pipeline[Int] ~> MultiplyBy(4)

      // Can only send if WebSocket is connected and user has entered text
      val send: Option[Callback] =
        for (ws <- s.ws if s.allowSend)
          yield sendMessage(ws, s.message)

      def sendOnEnter(e: ReactKeyboardEvent): Callback =
        CallbackOption.asEventDefault(e,
          CallbackOption.keyCodeSwitch(e) {
            case KeyCode.Enter => send.getOrEmpty
          }
        )

      <.div(
        <.div(
          ^.display.flex,
          <.input.text(
            ^.autoFocus  := true,
            ^.value      := s.message,
            ^.onChange  ==> onChange,
            ^.onKeyDown ==> sendOnEnter),
          <.button(
            ^.disabled  := send.isEmpty, // Disable button if unable to send
            ^.onClick -->? send,        // --> suffixed by ? because it's for Option[Callback]
            "Send"
          ),
          requestData(Pipeline[Int] ~> MultiplyBy(4) ~> ToString ~> DuplicateString, s.ws),
          requestData(Pipeline[Int] ~> MultiplyBy(4), s.ws)
        ),
        <.h4("Connection log"),
        <.pre(
          ^.width  := "83%",
          ^.height.auto,
          ^.border := "1px solid")(
          s.logLines.map(<.p(_)): _*)       // Display log
      )
    }

    private def requestData[A](pipeline: Pipeline[A], maybeWebSocket: Option[WebSocket]) = {
      val description = pipeline.xs.map(_.toString).mkString(" ~> ")
      val onClick = for (ws <- maybeWebSocket if ws.readyState == WebSocket.OPEN) yield sendMessage(ws, Json.stringify(pipeline.toJson))

      <.button(^.onClick -->? onClick, description)
    }

    def onChange(e: ReactEventFromInput): Callback = {
      val newMessage = e.target.value
      $.modState(_.copy(message = newMessage))
    }

    def sendMessage(ws: WebSocket, msg: String): Callback = {
      // Send a message to the WebSocket
      def send = Callback(ws.send(msg))

      // Update the log, clear the text box
      def updateState = $.modState(s => s.log(s"Sent: $msg").copy(message = ""))

      send >> updateState
    }

    def start: Callback = {

      // This will establish the connection and return the WebSocket
      def connect = CallbackTo[WebSocket] {

        // Get direct access so WebSockets API can modify state directly
        // (for access outside of a normal DOM/React callback).
        // This means that calls like .setState will now return Unit instead of Callback.
        val direct = $.withEffectsImpure

        // These are message-receiving events from the WebSocket "thread".

        def onopen(e: Event): Unit = {
          // Indicate the connection is open
          direct.modState(_.log("Connected."))
        }

        def onmessage(e: MessageEvent): Unit = {
          // Echo message received
          direct.modState(_.log(e.data.toString))
        }

        def onerror(e: Event): Unit = {
          // Display error message
          val msg: String =
            e.asInstanceOf[js.Dynamic]
              .message.asInstanceOf[js.UndefOr[String]]
              .fold(s"Error occurred!")("Error occurred: " + _)
          direct.modState(_.log(msg))
        }

        def onclose(e: CloseEvent): Unit = {
          // Close the connection
          direct.modState(_.copy(ws = None).log(s"""Closed. Reason = "${e.reason}""""))
        }

        // Create WebSocket and setup listeners
        val ws = new WebSocket(url)
        ws.onopen = onopen _
        ws.onclose = onclose _
        ws.onmessage = onmessage _
        ws.onerror = onerror _
        ws
      }

      // Here use attempt to catch any exceptions in connect
      connect.attempt.flatMap {
        case Right(ws)   => $.modState(_.log(s"Connecting to $url ...").copy(ws = Some(ws)))
        case Left(error) => $.modState(_.log(s"Error connecting: ${error.getMessage}"))
      }
    }

    def end: Callback = {
      def closeWebSocket = $.state.map(_.ws.foreach(_.close())).attempt
      def clearWebSocket = $.modState(_.copy(ws = None))
      closeWebSocket >> clearWebSocket
    }
  }

  val WebSocketsApp = ScalaComponent.builder[Unit]("WebSocket")
    .initialState(State(None, Vector.empty, ""))
    .renderBackend[Backend]
    .componentDidMount(_.backend.start)
    .componentWillUnmount(_.backend.end)
    .build
}