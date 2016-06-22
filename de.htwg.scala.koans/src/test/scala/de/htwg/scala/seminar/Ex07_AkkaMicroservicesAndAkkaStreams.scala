package de.htwg.akka_micoservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import org.codetask.koanlib.CodeTaskSuite

import scala.concurrent.duration._
import scala.concurrent.Await

class Ex07_AkkaMicroservicesAndAkkaStreams extends CodeTaskSuite("Akka Microservices and Akka Streams",7) {

  var requestResult = "Pong"


  koan("This is a simple HTTP microservice writen with akka. Whats your response when you call 127.0.0.1:9000/ping ?") {

    val ipAddress = "127.0.0.1"
    val port = 9000

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    def routes = {
      path("ping") {
        get {
          complete(StatusCodes.OK, "Pong")
        }
      } ~
        get {
          complete(
            HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Im a simple Microservice ...</h1>")
          )
        }
    }

    Http().bindAndHandle(routes, ipAddress, port)

    requestResult should be ("Pong")
  }

  koan("This is an complete Akka Stream. What do you get from the Sink output ?") {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    var output = 0

    val future = Source(1 to 2).map(_ * 2).runForeach( number => output += number)

    Await.result(future, 100 millis)  // Wait for end of stream

    output should be (6)
  }

  koan("This is also complete Akka Stream. What do you get from the Sink output ?") {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val future = Source(1 to 4).map(_ * 2).runWith(Sink.fold(0)(_ + _))

    val result = Await.result(future, 100 millis)  // Wait for end of stream

    result should be (20)
  }

  koan("A Akka Stream can split into his single parts Source/Flow/Sink. What is the result ?") {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    var result = 0

    val source = Source(1 to 5)
    val flow = Flow[Int].map(number => number + 2)
    val sink = Sink.foreach[Int](number => result += number)

    val future = source.via(flow).runWith(sink)

    Await.result(future, 100 millis)  // Wait for end of stream

    result should be (25)
  }


  koan("A long Akka Stream. What is the result ?") {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()


    val future = Source.single(3)
      .map(_ * 2).map(_ + 3).map(_ - 24).map(_ + 10).map(_ + 26).map(_ - 7).map(_ * 3)
      .runWith(Sink.head)

    val result = Await.result(future, 100 millis)  // Wait for end of stream

    result should be (42)
  }

  koan("Another tricky Akka Stream. What is the result ?") {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    var result = 0
    val sink = Sink.foreach[Int](number => result += number)

    val future = Source.empty.runWith(sink)

    Await.result(future, 100 millis)  // Wait for end of stream

    result should be (0)
  }

  koan("Combination of split and defined Akka Streams. What is the result ?") {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    var list = List(1, 2, 3)

    val source = Source(list)
    val sink = Sink.ignore

    val future = source.via(Flow[Int].map(_ * 2)).map(_ / 2).runWith(sink)

    Await.result(future, 100 millis)  // Wait for end of stream

    list should be (List(1, 2, 3))
  }

  var requestResultForDiv2 = "Success(4)"

  koan("This is also a Akka Microservice. Whats your response when you call 127.0.0.1:9000/div2/8 ?") {

    val ipAddress = "127.0.0.1"
    val port = 8080

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    def routes = {
      path("mul2" / IntNumber) { number =>
        get {
          complete{
            "Multiply result: " + Source.single(number).map(_ * 2).runWith(Sink.head)
          }
        }
      } ~
        path("div2" / IntNumber) { number =>
          get {
            complete(
              "Divide result: " + Source.single(number).map(_ / 2).runWith(Sink.head)
            )
          }
        }
    }

    Http().bindAndHandle(routes, ipAddress, port)

    requestResultForDiv2 should be ("Success(4)")
  }
}
