package sp.datapipeline

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import play.api.libs.json._
import sp.domain.Logic._
import sp.domain._
import sp.shared.DataAPI._
import sp.shared.{Pipe, Pipeline, TypedPipe}

object FrontendCode {


  def toSPValue[A: JSWrites] = Flow[A].map(x => Json.toJson(x))

  def test(system: ActorSystem): Unit = {
    implicit val materializer = ActorMaterializer()(system)


    // Frontend
    val builder = Pipeline[Int] ~> MultiplyBy(4) ~> ToString ~> DuplicateString
    val data = builder.toJson
    println(data)

    val builder2 = Pipeline[Int] ~> MultiplyBy(2)
    println(builder2.toJson)

    // Backend
    import Pipe.PipeFormat._
    val pipeline = data.as[List[Pipe]].reduce(_ ~> _)

    // From eg. Resource
    val source = Source(List(1, 2, 3))
    val sink = Sink.foreach[SPValue](println)

    source
      .via(toSPValue)
      .map(pipeline.flow)
      .runWith(sink)
  }

  def pipeFlow(pipes: Traversable[Pipe]): Flow[SPValue, SPValue, NotUsed] = Flow[SPValue].map(pipes.reduce(_ ~> _).flow)

  def pipeline[A: JSWrites, M](source: Source[A, NotUsed], sink: Sink[SPValue, M])(pipes: Traversable[Pipe]) = {
    Flow.fromSinkAndSource(sink, source).via(toSPValue).map(pipes.reduce(_ ~> _).flow)
  }
}
