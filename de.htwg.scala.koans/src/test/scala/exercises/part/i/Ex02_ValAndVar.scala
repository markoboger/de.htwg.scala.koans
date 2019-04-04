package de.htwg.scala.exercises

import org.codetask.koanlib.CodeTaskSuite

class Ex02_ValAndVar extends CodeTaskSuite("Val and Var",2) {
  
  koan("vars may be reassigned") {
    var a = 5
    a should be(5)

    a = 7
    a should be(7)
  }

  koan("vals may not be reassigned") {
    val a = 5
    a should be(5)

    // What happens if you uncomment these lines? compilation problem as a is immutable (reassignment to val)
    // a = 7
    // a should be (5)
  }


}
