package sp.controllers

import javax.inject._
import play.twirl.api.Html

import sp.shared.SharedMessages
import play.api.mvc._
import controllers._
import controllers.Assets.Asset

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

def versioned(s: String) = controllers.routes.Assets.versioned(s)
  def index = Action(Ok(AssetsHelper.index("SP-Core", versioned(_))))
  def serve(file: Asset) = controllers.Assets.versioned(path="/public", file: Asset)

}
