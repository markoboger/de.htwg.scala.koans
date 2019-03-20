package de.htwg.scala.seminar

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import org.codetask.koanlib.CodeTaskSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class Ex03_Actors extends CodeTaskSuite("Actors",3) {


  koan("First you create an actor system.") {

    val system = ActorSystem("ExampleActorSystem")

    system.name should be("ExampleActorSystem")
    system.terminate()
  }

  koan("You can create actors using the system. What do you get back?") {

    class ExampleActor extends Actor {

      override def receive = {
        case _ => //Do something
      }

    }

    val system = ActorSystem("ExampleActorSystem")
    val actor = system.actorOf(Props(new ExampleActor), "ExampleActor")

    actor.isInstanceOf[Actor] should be(false)
    actor.isInstanceOf[ExampleActor] should be(false)
    actor.isInstanceOf[ActorRef] should be(true)
    actor.isInstanceOf[ActorPath] should be(false)

    system.terminate()
  }

  koan("You can ask an actor to send a message back") {

    class ExampleActor extends Actor {

      override def receive = {
        case message: String => sender.tell(message, self)
        case _ => sender ! "?"
      }

    }

    val system = ActorSystem("ExampleActorSystem")
    val actor = system.actorOf(Props(new ExampleActor), "ExampleActor")
    implicit val timeout = Timeout(5 seconds)

    val future1: Future[Any] = actor ? "Hello"
    val future2: Future[Any] = actor ? 42

    future1 onSuccess {
      case result => result should be("Hello")
    }

    future2 onSuccess {
      case result => result should be("?")
    }

    Await.result(future1, 1 seconds)
    Await.result(future2, 1 seconds)
    system.terminate()
  }

  koan("You can use case objects or case classes") {

    case class TextMessage(text: String)

    case object GiveMeAnAnswerMessage

    class ExampleActor extends Actor {

      override def receive = {
        case message: TextMessage => sender.tell(message.text, self)
        case GiveMeAnAnswerMessage => sender ! 42
      }

    }

    val system = ActorSystem("ExampleActorSystem")
    val actor = system.actorOf(Props(new ExampleActor), "ExampleActor")
    implicit val timeout = Timeout(5 seconds)

    val future1: Future[Any] = actor ? TextMessage("Hello")
    val future2: Future[Any] = actor ? GiveMeAnAnswerMessage

    future1 onSuccess {
      case result => result should be("Hello")
    }

    future2 onSuccess {
      case result => result should be(42)
    }

    Await.result(future1, 1 seconds)
    Await.result(future2, 1 seconds)
    system.terminate()
  }

  koan("You can pass parameters to the actors constructor") {

    case object AskNameMessage

    class ExampleActor(private val name: String) extends Actor {

      override def receive = {
        case AskNameMessage => sender.tell(name, self)
      }

    }

    val system = ActorSystem("ExampleActorSystem")
    val actor = system.actorOf(Props(new ExampleActor("Max")), "MaxTheExampleActor")
    implicit val timeout = Timeout(5 seconds)

    val future: Future[Any] = actor ? AskNameMessage

    future onSuccess {
      case result => result should be("Max")
    }

    Await.result(future, 1 seconds)
    system.terminate()
  }

  koan("Actors can have a state") {

    class NumberActor extends Actor {

      var number: Int = 0


      override def receive = {
        case n: Int =>
          number += n
          sender ! number
        case _ =>
          sender ! 42
      }

    }

    val system = ActorSystem("NumberActorSystem")
    val actor = system.actorOf(Props(new NumberActor), "NumberActor")
    implicit val timeout = Timeout(5 seconds)

    val future1: Future[Any] = actor ? 5
    future1 onSuccess {
      case result => result should be(5)
    }

    Await.result(future1, 1 seconds)

    val future2: Future[Any] = actor ? "6"
    val future3: Future[Any] = actor ? 6

    future2 onSuccess {
      case result => result should be(42)
    }

    future3 onSuccess {
      case result => result should be(11)
    }

    Await.result(future2, 5 seconds)
    Await.result(future3, 5 seconds)

    system.terminate()
  }

  koan("Actors can create other actors. That actors are the childs of the creating actor.") {

    case object HelloMessage

    class ParentActor extends Actor {

      var n: Int = 0

      override def receive = {
        case HelloMessage =>
          //new is not recommended here
          val child = context.actorOf(Props(new ChildActor), "child" + n)
          n += 1
          child.tell(HelloMessage, sender)
      }

    }

    class ChildActor extends Actor {

      override def receive = {
        case HelloMessage => sender.tell("Hello", self)
          context.stop(self)
      }

    }

    val system = ActorSystem("HierarchyActorSystem")
    val actor = system.actorOf(Props(new ParentActor), "Parent")
    implicit val timeout = Timeout(5 seconds)

    val future1: Future[Any] = actor ? HelloMessage

    future1 onSuccess {
      case result => result should be("Hello")
    }

    system.terminate()
  }

  koan("Actors have paths") {

    case object SendPathMessage
    case object SendPathOfChildMessage

    class ParentActor extends Actor {
      implicit val timeout = Timeout(5 seconds)

      //new is not recommended here
      val child = context.actorOf(Props(new ChildActor), "child")

      override def receive = {
        case SendPathMessage => sender ! self.path.toString
        case SendPathOfChildMessage =>
          val future: Future[Any] = child ? SendPathMessage
          sender ! future
      }

    }

    class ChildActor extends Actor {

      override def receive = {
        case SendPathMessage => sender ! self.path.toString
      }

    }

    val system = ActorSystem("ActorSystem")
    val actor = system.actorOf(Props(new ParentActor), "Parent")
    implicit val timeout = Timeout(5 seconds)

    actor.path.toString should be("akka://ActorSystem/user/Parent")

    val future1: Future[Any] = actor ? SendPathMessage
    val future2: Future[Any] = actor ? SendPathOfChildMessage

    future1 onSuccess {
      case result => result should be("akka://ActorSystem/user/Parent")
    }

    future2 onSuccess {
      case result: Future[Any] => result.onSuccess {
        case result => result should be("akka://ActorSystem/user/Parent/child")
      }
    }

    actor.path.isInstanceOf[ActorPath] should be(true)

    Await.result(future1, 1 seconds)
    Await.result(future2, 5 seconds)

    system.terminate()
  }

  koan("You can select actors using a path") {
    case class TextMessage(text: String)


    class ExampleParentActor extends Actor {

      val child = context.actorOf(Props(new ChildActor), "ExampleChild")

      override def receive = {
        case _ => //Do noting
      }

    }

    class ChildActor extends Actor {

      override def receive = {
        case message: TextMessage => sender.tell(message.text, self)
      }

    }

    val system = ActorSystem("ExampleActorSystem")
    system.actorOf(Props(new ExampleParentActor), "ExampleParentActor")
    implicit val timeout = Timeout(5 seconds)

    val selectedActor = system.actorSelection("akka://ExampleActorSystem/user/ExampleParentActor/ExampleChild")

    val future: Future[Any] = selectedActor ? TextMessage("Hello")

    future onSuccess {
      case result => result should be("Hello")
    }

    Await.result(future, 1 seconds)
    system.terminate()
  }

}
