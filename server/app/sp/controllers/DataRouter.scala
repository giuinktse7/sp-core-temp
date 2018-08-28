package sp.controllers

import javax.inject.Inject
import play.api.mvc.{Action, Results}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class DataRouter @Inject()(controller: Application) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/topic/$topic") => Action(Results.Ok(topic))
  }
}