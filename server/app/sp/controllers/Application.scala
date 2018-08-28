package sp.controllers

import javax.inject._
import play.twirl.api.Html

import sp.shared.SharedMessages
import play.api.mvc._
import controllers._
import controllers.Assets.Asset

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

def versioned(s: String) = controllers.routes.Assets.versioned(s)
  def index = Action(Ok(AssetsHelper.index2("SP-Core", versioned)))
}

class TestRouter extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/versionedAssets/$file") => Assets.versioned(file)
    case GET(p"/assets/$file") => Assets.at(file)
  }
}