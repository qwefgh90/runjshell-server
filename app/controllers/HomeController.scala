package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.mvc._
import akka.stream.scaladsl._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
//  def index() = Action { implicit request: Request[AnyContent] =>
//    Ok(views.html.index())
//  }

  def websocket = WebSocket.accept[String, String] { request =>

    // Log events to the console
//    val in = Sink.foreach[String](println)

    // Send a single 'Hello!' message and then leave the socket open
//    val out = Source.single("Hello!").concat(Source.maybe)
    Flow.fromFunction(a => a);
//    Flow.fromSinkAndSource(in, out)
  }
}
