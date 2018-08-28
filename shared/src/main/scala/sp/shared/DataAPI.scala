package sp.shared

import play.api.libs.json.Json
import sp.domain.JSFormat
import sp.domain.Logic.deriveCaseObject

object DataAPI {
  case object ToString extends TypedPipe[Int, String] {
    override def f = _.toString

    def name = productPrefix
    def json = infer[ToString.type](this)(deriveCaseObject[ToString.type])
  }

  case class MultiplyBy(n: Int) extends TypedPipe[Int, Int] {
    override def f = _ * n

    import MultiplyBy.format

    override def name = productPrefix
    override def json = infer[MultiplyBy](this)
  }

  object MultiplyBy {
    implicit lazy val format: JSFormat[MultiplyBy] = Json.format[MultiplyBy]
  }

  case object DuplicateString extends TypedPipe[String, String] {
    override def f = s => s + s

    override def json = infer[DuplicateString.type](this)(deriveCaseObject[DuplicateString.type])
    override def name = productPrefix
  }
}
