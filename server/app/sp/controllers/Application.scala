package sp.controllers

import javax.inject._
import play.twirl.api.Html
import sp.shared.SharedMessages
import play.api.mvc._
import controllers._
import controllers.Assets.Asset
import javax.inject.Inject
import play.api.ApplicationLoader.Context
import play.api.i18n.I18nComponents
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import play.api.routing.Router.Routes
import play.api.routing.{Router, SimpleRouter}
import play.api.routing.sird._
import play.filters.HttpFiltersComponents


@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index = Assets.at("index.html")
}