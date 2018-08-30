package sp

import play.api.libs.json.Json
import sp.Pipe.PipeFormat._
import sp.domain.{JSFormat, SPValue}

object Pipeline {
  def apply[A: JSFormat]: Pipeline[A] = Pipeline[A]()
  def apply(pipes: List[Pipe]): SPValue => SPValue = pipes.foldLeft((x: SPValue) => x)((a, b) => a andThen b.flow)
}


case class Pipeline[A](xs: List[Pipe] = List())(implicit fa: JSFormat[A]) {
  def ~>[B: JSFormat](x: TypedPipe[A, B] with Pipe): Pipeline[B] = {
    Pipe.registerPipe(x)
    Pipeline(x :: xs)
  }

  def toJson: SPValue = Json.toJson(xs.reverse)
}
