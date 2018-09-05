package controllers.spcore.datapipeline

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}
import org.codehaus.jackson.JsonParseException
import play.api.libs.json.{JsError, JsResult, JsSuccess, Json}
import sp.Pipe
import sp.Pipe.PipeFormat._
import sp.domain.{JSFormat, SPValue}
import controllers.spcore.websocket.HoldWithInitial

object WebSocketConnection {

  def parse(raw: String): JsResult[Pipe] = {
    println(s"Parsing $raw")
    try {
      println(Json.parse(raw).as[List[Pipe]])
      JsSuccess(Json.parse(raw).as[List[Pipe]].reduce(_ ~> _))
    } catch {
      case _: JsonParseException => JsError("Could not parse request.")
      case e =>
        e.printStackTrace()
        JsError("Error while trying to parse the json as as list of Pipes.")
    }
  }

  // TODO Handle errors.
  def pipelineFlow[A: JSFormat](source: Source[A, NotUsed]) = {
    val dataSource = source.map(x => Json.toJson(x))
    val pipelineDescription = Flow[String].map(x => Some(parse(x)))

    val pipeFlow = pipelineDescription
      .via(HoldWithInitial(None))
      .collect { case Some(x) => x }

    val withoutErrors: Flow[String, Pipe, NotUsed] = pipeFlow.collect { case JsSuccess(value, _) => value }
    val res: Flow[String, SPValue, NotUsed] = withoutErrors.zipWith(dataSource)(_ flow _)


    res.map(Json.stringify)
  }
}
