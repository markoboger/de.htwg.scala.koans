package de.htwg.seminar

import org.codetask.koanlib.CodeTaskSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class Ex02_FuturesAndPromises extends CodeTaskSuite("Futures and Promises",2) {

  koan("a callback registered with \"onSuccess\" is called when the future completes successfully") {

    val future: Future[String] = Future {
      "future result"
    }

    future onSuccess {
      case result => result should be("future result")
    }

    Await.ready(future, 1 seconds)

  }



  koan("exceptions can be handled by registering a callback with \"onFailure\"") {
    val future: Future[String] = Future {
      throw new Exception("an exception")
    }

    future onFailure {
      case ex => ex.getMessage should be("an exception")
    }

    Await.ready(future, 1 seconds)
  }

  koan("both cases can be handled with \"onComplete\"") {
    val future: Future[String] = Future {
      "not failing"
    }

    future onComplete {
      case Success(result) => result should be("not failing")
      case Failure(_) => println("try to get this printed to the console")
    }

    Await.ready(future, 1 seconds)
  }


  koan("futures are executed asynchronously") {
    var someBoolean = false

    val future: Future[Boolean] = Future {
      Thread.sleep(500)
      true
    }

    future onSuccess {
      case result => someBoolean = result
    }

    future.isCompleted should be(false)
    someBoolean should be(false)

    Thread.sleep(1000)

    future.isCompleted should be(true)
    someBoolean should be(true)

  }

  koan("for comprehension work with futures") {
    val future1 = Future {
      1
    }

    val future2 = Future {
      2
    }

    val future3 = for {
      x <- future1
      y <- future2
    } yield {
      x + y
    }

    future3 onSuccess {
      case number => number should be(3)
    }

    Await.ready(future3, 1 seconds)

  }

  koan("promises provide another way to create futures") {
    val promise = Promise[Int]()
    val future = promise.future

    future onSuccess {
      case number => number should be(42)
    }

    promise success (42)

    Await.ready(future, 1 seconds)

  }

  koan("a promise can be failed with \"failure\"") {
    val promise = Promise[Int]()
    val future = promise.future


    future recover {
      case ex => ex.getMessage should be("promise failed"); 42
    } onSuccess {
      case number => number should be(42)
    }

    promise failure (new Exception("promise failed"))

    Await.ready(future, 1 seconds)

  }
}
