package sp.controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api.mvc._
import controllers._
import javax.inject.Inject
import sp.datapipeline.FrontendCode


@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val system = ActorSystem("SP")

  FrontendCode.test(system)

  def index = Assets.at("index.html")
}