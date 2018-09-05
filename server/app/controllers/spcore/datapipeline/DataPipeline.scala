package controllers.spcore.datapipeline

import akka.NotUsed
import akka.stream.scaladsl.Source
import play.api.http.HeaderNames
import play.api.mvc.{Results, WebSocket}
import sp.domain.SPValue

import scala.concurrent.Future

/**
  * Entry point for the pipeline API
  */
object DataPipeline {
  /**
    * Serves data from a Source using a WebSocket.
    */
  def serveData(dataSource: PartialFunction[String, Source[SPValue, NotUsed]], topic: String): WebSocket = {
    WebSocket.acceptOrResult(req => {
      println(req.headers.get(HeaderNames.UPGRADE))
      Future.successful {
        if (dataSource.isDefinedAt(topic)) Right(WebSocketConnection.pipelineFlow(dataSource(topic)))
        else Left(Results.NotFound(s"There is no source for the topic $topic."))
      }
    })
  }
}
