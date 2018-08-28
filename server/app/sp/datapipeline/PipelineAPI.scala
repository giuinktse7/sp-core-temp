package sp.datapipeline

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import scala.concurrent.Future
import scala.util.{Failure, Success}

sealed trait StorageResult
case class StorageSuccess[A](savedValue: A) extends StorageResult
case class StorageFailure(errors: List[String]) extends StorageResult


case class Point(x: Int, y: Int, tag: String)

trait Storable[A, Query[_]] {
  /**
    * Query for saving an A
    * @param a value to save
    * @return A query that when run, will save A into a database.
    */
  def saveQuery(a: A): Query[A]
}

trait Database[F[_], QueryType[_]] {
  def get[A](query: QueryType[A]): F[A]
  def put[A](query: QueryType[A]): F[StorageResult]

  def putFlow[S](implicit S: Storable[S, QueryType]): Flow[S, F[StorageResult], NotUsed] = Flow[S].map(S.saveQuery(_)).map(put)
}

trait DataPoint[F[_]] {
}


object PipelineAPI {
  import scala.concurrent.ExecutionContext.Implicits.global


  case class TestQuery[A](value: A, query: String)

  def test(system: ActorSystem) = {
    implicit val materializer = ActorMaterializer()(system)

    println("in")
    type Id[A] = A

    implicit val storePoint = new Storable[Point, TestQuery] {
      override def saveQuery(a: Point) = TestQuery(a, s"Save my Point(${a.x}, ${a.y}, ${a.tag})")
    }

    val identityDB = new Database[Id, Id] {
      override def get[A](query: A): A = query
      override def put[A](query: A): Id[StorageResult] = StorageSuccess(query)
    }

    val futureDB = new Database[Future, TestQuery] {
      import scala.concurrent.duration._

      def delay[A](time: FiniteDuration, value: A) = {
        akka.pattern.after(time, using = system.scheduler)(Future.successful(value))
      }

      override def get[A](query: TestQuery[A]): Future[A] = delay(0.5 seconds, query.value)
      override def put[A](query: TestQuery[A]): Future[StorageSuccess[A]] = delay(0.5 seconds, StorageSuccess(query.value))
    }

    val source: Source[Point, NotUsed] = Source(List(
        Point(0, 0, "0,0"),
        Point(1, 0, "1,0"),
        Point(0, 1, "0,1"),
        Point(1, 1, "1,1")
      ))

    // val consResult = Sink.fold[List[StorageResult], StorageResult](Nil)((a, b) => b :: a)
    val printEach = Sink.foreach[Future[StorageResult]](f => f.onComplete {
      case Success(v) => println(v)
      case Failure(e) => println(e)
    })

    println(Util.caseClassParamsOf[Point])

    val test = source.via(futureDB.putFlow[Point]).toMat(printEach)(Keep.right)

    test.run()
  }



}
