package sp.datapipeline

import play.api.libs.json.Json
import sp.domain.JSFormat

/**
  * Code that will be shared between frontend and backend
  */
object Shared {
  trait Combinable[A, B] {
    def combine(a1: A, a2: A): B
  }

  sealed trait Part

  case object Nothing extends Part
  trait Sink[-B] extends Part {
    def accept(b: B): Unit
  }


  sealed trait Op[A, B] {
  }

  trait Map[A, B] extends Op[A, B] {
    def flow: A => B
  }

  trait Batch[F[_], A] extends Op[A, F[A]] {
    def batch(fa: F[A], a: A): F[A]
  }

  def listBatch[A] = new Batch[List, A] {
    override def batch(fa: List[A], a: A) = a :: fa
  }

  def identity[A] = new Map[A, A] {
    override def flow = a => a
  }

  def printSink[B] = new Sink[B] {
    override def accept(b: B): Unit = println(b)
  }

  object PointOps {
    case class AddNumber(n: Int) extends Map[Int, Int] {
      override def flow = _ + n
    }

    implicit val format: JSFormat[AddNumber] = Json.format[AddNumber]
  }


  // Points, sum x and y, replace tag

  def pipeline[B](implicit Pipe: Combinable[B, B]) = Pipe

  implicit val combinePoints = new Combinable[Point, Point] {
    override def combine(a1: Point, a2: Point) = Point(a1.x + a2.x, a1.y + a2.y, a2.tag)
  }
}
