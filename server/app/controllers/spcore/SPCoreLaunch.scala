package controllers.spcore

import akka.actor.ActorSystem
import javax.inject.{Inject, _}
import play.api.mvc._
import controllers.spcore.datapipeline.FrontendCode


@Singleton
class SPCoreLaunch @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  println("PlayService started.")
  implicit val system = ActorSystem("SP")

  FrontendCode.test(system)

  def index(asDependency: Boolean = true) = Action(Ok(views.html.index(asDependency)))
}