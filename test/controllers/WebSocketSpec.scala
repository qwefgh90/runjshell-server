package test.controllers

import java.util.concurrent.{ArrayBlockingQueue, Callable}
import java.util.function.Consumer

import play.shaded.ahc.org.asynchttpclient.AsyncHttpClient
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play._
import play.api.Logger
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{Helpers, TestServer, WsTestClient}
import test.util.WebSocketClient

import scala.concurrent.{Future, blocking}
//import org.awaitility.Awaitility._
import play.api.libs.json._

import scala.compat.java8.FutureConverters
import scala.concurrent.Await
import scala.concurrent.duration._


class WebSocketSpec extends PlaySpec with ScalaFutures {

  "HomeController" should {
    /**
      * https://github.com/playframework/play-scala-websocket-example/tree/2.6.x/test/controllers
      * We can find an test example of webscoket
      */
    "accept incoming request" in WsTestClient.withClient { client =>
      lazy val port: Int = 31337
      val app = new GuiceApplicationBuilder().build()
      Helpers.running(TestServer(port, app)) {
        val myPublicAddress = s"localhost:$port"
        val serverURL = s"ws://$myPublicAddress/ws"
        val queue = new ArrayBlockingQueue[String](10)
        val asyncHttpClient: AsyncHttpClient = client.underlying[AsyncHttpClient]
        val webSocketClient = new WebSocketClient(asyncHttpClient)
        try {
          val origin = serverURL
          val consumer: Consumer[String] = new Consumer[String] {
            override def accept(message: String): Unit = queue.put(message)
          }
          val listener = new WebSocketClient.LoggingListener(consumer)
          val completionStage = webSocketClient.call(serverURL, origin, listener)
          val f = FutureConverters.toScala(completionStage)
          whenReady(f, timeout = Timeout(2.second)) { webSocket =>
            val condition: Callable[java.lang.Boolean] = new Callable[java.lang.Boolean] {
              override def call(): java.lang.Boolean = webSocket.isOpen && queue.peek() != null
            }
            webSocket.isOpen mustBe true
            webSocket.sendMessage("hello!")
            val input: String = queue.take()
            input mustBe "hello!"
//            val json:JsValue = Json.parse(input)
//            val symbol = (json \ "symbol").as[String]
//            List(symbol) must contain oneOf("AAPL", "GOOG", "ORCL")
          }
        } catch {
          case e: IllegalStateException =>
            fail("Something's wrong.",e)
          case e: java.util.concurrent.ExecutionException =>
            fail("Something's wrong.",e)
        }
      }
    }

//    "close connection after timeout" in WsTestClient.withClient { client =>
//      lazy val port: Int = 31338
//      val app = new GuiceApplicationBuilder()
//        .configure("play.server.http.idleTimeout" -> "2s")
//        .build()
//      Helpers.running(TestServer(port, app)) {
//        val myPublicAddress = s"localhost:$port"
//        val serverURL = s"ws://$myPublicAddress/ws"
//        val queue = new ArrayBlockingQueue[String](10)
//        val asyncHttpClient: AsyncHttpClient = client.underlying[AsyncHttpClient]
//        val webSocketClient = new WebSocketClient(asyncHttpClient)
//        try {
//          val origin = serverURL
//          val consumer: Consumer[String] = new Consumer[String] {
//            override def accept(message: String): Unit = queue.put(message)
//          }
//          val listener = new WebSocketClient.LoggingListener(consumer)
//          val completionStage = webSocketClient.call(serverURL, origin, listener)
//          val f = FutureConverters.toScala(completionStage)
//          whenReady(f, timeout = Timeout(2.second)) { webSocket =>
//            webSocket.isOpen mustBe true
//            Thread.sleep(10000)
//            Logger.debug("Closed??")
//            webSocket.isOpen mustBe false
//          }
//        } catch {
//          case e: IllegalStateException =>
//            fail("Something's wrong.",e)
//          case e: java.util.concurrent.ExecutionException =>
//            fail("Something's wrong.",e)
//        }
//      }
//    }
  }

}