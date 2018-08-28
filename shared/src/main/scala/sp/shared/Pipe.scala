package sp.shared

import play.api.libs.json._
import sp.domain._

trait Pipe { self =>
  def name: String
  def flow: SPValue => SPValue
  def runWith(v: SPValue) = flow(v)
  def ~>(pipe: Pipe) = new Pipe {
    override def name = name + " ~> " + pipe.name

    override def flow = self.flow andThen pipe.flow
  }
}

abstract class TypedPipe[A: JSFormat, B: JSFormat] extends Pipe {
  def f: A => B
  override def flow = v => Json.toJson(f(v.as[A]))
  def from[T: JSReads] = (v: SPValue) => Json.fromJson[T](v)
  def to[P <: Pipe: JSWrites](pipe: P)() = Json.toJson(pipe)
  def json: (JsValue => JsResult[Pipe], SPValue)
  def getFromJson = json._1
  def makeJson = json._2
  def infer[P <: Pipe : JSFormat](p: P) = (from[P], to(p))

}


object Pipe {
  /**
    * Internal API
    */
  def registerPipe[A, B](pipe: TypedPipe[A, B]): Unit = Formats.updateFormat(pipe)

  private object Formats {
    private type PipeName = String

    /**
      * Internal API.
      */
    def updateFormat[A, B](pipe: TypedPipe[A, B]): Unit = {
      if (!writesFunction.isDefinedAt(pipe)) writesFunction = writesFunction.orElse {
        case k if pipe.name == k.name => (pipe.name, pipe.makeJson)
      }

      if (!readsFunction.isDefinedAt((pipe.name, SPAttributes.empty))) readsFunction = readsFunction.orElse {
        case (name, data) if name == pipe.name => pipe.getFromJson(data)
      }
    }

    private var readsFunction: PartialFunction[(PipeName, SPAttributes), JsResult[Pipe]] = new PartialFunction[(PipeName, SPAttributes), JsResult[Pipe]] {
      override def isDefinedAt(x: (PipeName, SPAttributes)) = false
      override def apply(v1: (PipeName, SPAttributes)) = JsError(s"No matching partial function for $v1")
    }

    private var writesFunction: PartialFunction[Pipe, (PipeName, SPValue)] = new PartialFunction[Pipe, (PipeName, SPValue)] {
      override def isDefinedAt(x: Pipe) = false

      override def apply(v1: Pipe) = ("None", JsNull)
    }

    def fPipe = new Format[Pipe] {
      override def writes(o: Pipe) = {
        val (name, data) = writesFunction(o)
        JsObject(Seq("class" -> JsString(name), "data" -> data))
      }

      private val recoveryFn: PartialFunction[(String, SPAttributes), JsResult[Pipe]] = {
        case (_, json) => JsError(s"Unexpected JSON value $json")
      }

      private def from(name: String, data: JsObject): JsResult[Pipe] = {
        readsFunction.orElse(recoveryFn)((name, data))
      }

      override def reads(json: SPValue) = {
        for {
          name <- (json \ "class").validate[String]
          data <- (json \ "data").validate[JsObject]
          result <- from(name, data)
        } yield result
      }
    }
  }

  object PipeFormat {
    implicit lazy val fPipe: JSFormat[Pipe] = Formats.fPipe
  }
}