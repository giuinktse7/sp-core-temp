package sp

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._
import sp.controllers.Application
import sp.datapipeline.DataPipeline
import sp.domain.{JSWrites, SPValue}

import scala.util.Random
import scala.concurrent.duration._

class DataRouter @Inject()(controller: Application) extends SimpleRouter {
  val intSource = Source.repeat(100).map(x => Math.floor(Random.nextDouble() * x)).throttle(1, 2 seconds)
  val stringSource = Source.repeat(100).map(_ => Random.nextPrintableChar().toString).throttle(1, 2.seconds)

  val dataSources: PartialFunction[String, Source[SPValue, NotUsed]] = {
    case "test" => intSource.via(toJson)
  }

  override def routes: Routes = {
    case GET(p"/$topic") if dataSources.isDefinedAt(topic) => DataPipeline.serveData(dataSources, topic)
  }

  def toJson[A: JSWrites] = Flow[A].map(a => Json.toJson(a))

}