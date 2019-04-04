package de.htwg.scala.exercises

import org.codetask.koanlib.CodeTaskSuite

class Ex29_Preconditions extends CodeTaskSuite("Preconditions",31) {

  class WithParameterRequirement(val myValue: Int) {
    require(myValue != 0)

    def this(someValue: String) {
      this (1)
    }
  }

  // Instruction: use Intercept to catch the type of exception thrown by an invalid precondition
  koan("On precondition violation, intercept expects type of exception thrown") {
    intercept[IllegalArgumentException] {
      val myInstance = new WithParameterRequirement(0)
    }

    val myInstance = new WithParameterRequirement("")// does not break as we use the alternate constructor which discard the input and set the value 1 anyway
  }
}
