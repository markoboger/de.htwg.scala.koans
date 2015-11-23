package de.htwg.scala.koans.support

import org.scalatest.exceptions.TestPendingException
import org.scalatest.{Tracker, Stopper, Reporter, FunSuite, Args, Status, SucceededStatus}
import org.scalatest.Matchers
import org.scalatest.matchers.Matcher
import org.scalatest.events.{TestPending, TestFailed, TestIgnored, Event}

trait KoanSuite extends FunSuite with Matchers {

  def koan(name : String)(fun: => Unit) { test(name.stripMargin('|'))(fun) }

  def meditate() = pending

  def  __ : Matcher[Any] = {
    throw new TestPendingException
  }

  protected class ___ extends Exception {
    override def toString = "___"
  }

  private class ReportToTheMaster(other: Reporter) extends Reporter {
    var failed = false
    def failure(event: Master.HasTestNameAndSuiteName) {
      failed = true
      info("*****************************************")
      info("*****************************************")
      info("")
      info("")
      info("")
      info(Master.studentFailed(event))
      info("")
      info("")
      info("")
      info("*****************************************")
      info("*****************************************")
    }

    def apply(event: Event) {
      event match {
        case e: TestIgnored => failure(event.asInstanceOf[Master.HasTestNameAndSuiteName])
        case e: TestFailed => failure(event.asInstanceOf[Master.HasTestNameAndSuiteName])
        case e: TestPending => failure(event.asInstanceOf[Master.HasTestNameAndSuiteName])
        case _ => other(event)
      }

    }
  }

  override def run(testName: Option[String], args:Args):Status = {
    var status = SucceededStatus
    if (!Master.studentNeedsToMeditate) {
      super.run(testName, args)
    }
    status
  }
  
  class Quiz(x:Int) {
    def asQuiz =  x
    def ? = x
  }
  implicit def int2Quiz(x:Int) = new Quiz(x) 
//  implicit def bool2Quiz(x:Boolean) = new Quiz(x)

}
