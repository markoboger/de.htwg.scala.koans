package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class Functions extends CodeTaskSuite("Functions", 3) {
  koan(""" Functions:
    The bread and butter of Scala are functions easily that it's a functional programming language. 
    To create a function you define a function with the keyword def. A function can consist of a single 
    line or multiple lines building a code block. Due to Scala all functions should return a value 
    otherwise they could create side effects.""") {
    def square(x: Double): Double = x * x

    square(3.0) should be(9.0)

    def add(x: Int, y: Int, z: Int): Int = {
      x + y + z
    }

    add(3, 2, 1) should be(6)
  }

  koan(""" Lambda Literals: 
    Lambda literals are function literals. You could see it as a inline function.
    They are mostly defined (exception s. last statement) with a symbol called rocket (=>).
    In special cases you could use the buildin wildcard feature (s. last statement).
    Lambda literals can also be called as anonymous functions""") {
    val square = (x: Double) => x * x
    square(3) should be(9)

    val add = (x: Double, y: Double) => x + y
    add(3, 4) should be(7)

    val func = (_: Double) * 2
    func(25) should be(50)
  }

  koan(""" Higher Order Functions:
    Higher order functions are based on lambda literals. 
    Basic usecase: 
      You have a set of numbers and you want to add, multiply and subtract them.
      You could write a function for each arithmetic function or you would use the
      benefits of lambda literals. 
      
    In this example the user can choose which arithmetic function will be applied 
    to the dataset x,y,z by passing an lambda literal as an argument.
    
    The two last statements make use of the wildcard feature which shortens the function call and 
    enhances the readability""") {
    def combine(x: Int, y: Int, z: Int, f: (Int, Int) => Int): Int = f(f(x, y), z)
    combine(2, 3, 4, (x, y) => x + y) should be(9)
    combine(2, 3, 4, (x, y) => x * y) should be(24)
    combine(2, 3, 4, _ * _) should be(24)
    combine(2, 3, 4, _ min _) should be(2)
  }

  codetask(""" Exercise: (String operations) 
    Write a function which disassembles a given time string. The result should be a tuple containing 
    the hours and minutes""") {
    def splitTime(time: String): (Int, Int) = {
      val colonPosition = time.indexOf(":")
      //solve
      val hours = time.substring(0, colonPosition).toInt
      val minutes = time.substring(colonPosition + 1).toInt
      (hours, minutes)
      //endsolve
    }
    // test
    splitTime("12:55") should be((12, 55))
    // endtest
  }

  koan(""" Type Declarations (Alias)
    Scala offers with the keyword type a way to shorten variable types with aliases. In the example below 
    you see how to alias a three tuple. Aliases can make your code more readable. Keep in mind that aliaes don't
    have any logic. In a later chapter we learn about case classes which will be even better.""") {
    type Vect3 = (Double, Double, Double)
    def addVect(v1: Vect3, v2: Vect3): Vect3 = { (v1._1 + v2._1, v1._2 + v2._2, v1._3 + v2._3) }
    val v1: Vect3 = (2, 3, 3)
    val v2: Vect3 = (4, 7, 7)

    addVect(v1, v2) should be((6, 10, 10))
  }
}