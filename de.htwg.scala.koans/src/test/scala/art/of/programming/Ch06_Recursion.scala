package art.of.programming

import org.codetask.koanlib.CodeTaskSuite

class Ch06_Recursion extends CodeTaskSuite("Recursion", 4) {
  
  video("Basic idea behind recursion", "qeh2bbPqKaI")
  video("Recursion in Scala", "mIi5M_QiVXs")
  video("Additional Recursive functions", "YEVyoXa35e8")
  video("Tracing recursive functions", "3xO1F2Jj5kg")
  video("Tracing by substitution", "sl4H7PhUSWk")
  koan(""" Recursive Functions:
    Recursive calling of functions within the body of this method is as simple as in other languages. Recursive function must have a return
    value. 
    
    Note: During calculation of factorial values the value space of int is surely to be reach an overflow. If you want to be sure to 
    calculate the right value make use of the datatype BigInt""") {
    def factorial(n: Int): Int = if (n < 2) 1 else n * factorial(n - 1)
    factorial(5) should be(120)

    def squareSum(n: Int): Int = if (n < 2) 1 else n * n + squareSum(n - 1)
    squareSum(3) should be(14)
  }

  koan(""" Nested Functions:
   Scala give you the ability to define nested functions. This limits the scope of use to inside of definition block. 
   It is mainly used to improve readability of code that uses several higher-order functions. In contrast to anonymous function literals, this allows to give 
   them meaningful names before passing them to another function.""") {
    def factorial(i: Int): Int = {
      def fact(i: Int, accumulator: Int): Int = {
        if (i <= 1)
          accumulator
        else
          fact(i - 1, i * accumulator)
      }
      fact(i, 1)
    }
    factorial(5) should be(120)
  }

  video("The match expression", "jemq2R67Wvw")
  video("Options for a match expression", "7bkOO6wKqUY")
  koan(""" Match Expression:
    A match expression could be described in a vaque way as a switch pattern like in java but way more powerful. It uses the build-in 
    pattern matching mechanism, which allows to match a given set of data with predefined matches.    
    The match expression could be used in cases where pattern matching is already in use, e.g. tuples or case classes.
    
    Note: You can use a variable name instead of the wildcard, in case you want to reference the variable in the matched code block.""") {
    def fact(n: Int): Int = n match {
      case 0 => 1
      case _ => n * fact(n - 1)
    }
    fact(6) should be(720)

    def sumSquare(n: Int): Int = n match {
      case 1 => 1
      case _ => n * n + sumSquare(n - 1)
    }
    sumSquare(3)

    val result = (1, 5) match {
      case (0, 0) => 0
      case (3, 1) => 3
      case (2, n) => n * 1
      case (1, _) => 42
    }
    result should be(42)
  }

  koan(""" If guards:
    If guards are just a simple addition to the known match expression in which you could 
    add further conditionals to each case to enhance logic.""") {
    val surnameResponse = ("Max", "Mustermann") match {
      case ("Max", n) if n.startsWith("M") => 0
      case ("Max", n) if n.startsWith("X") => 42
    }
    surnameResponse should be(0)
  }

  video("Recursive Fizz Buzz", "5sryc6JUe6M")
  codetask(""" Exercise: (Recursive FizzBuzz)
    Write a recursive function which calculates the Fizz Buzz Game for the first 100 integers.
    FizzBuzz is a simple game in which you:
    
    Shout Fizz when the number is divideable by 3,
    Shout Buzz when the number is divideable by 5,
    and FizzBuzz when both, otherwise shout the current number.""") {
    def fizzBuzz(x: Int): String = {
      //solve
      if (x <= 100) {
        (x % 3, x % 5) match {
          case (0, 0) => "FizzBuzz"
          case (0, _) => "Fizz"
          case (_, 0) => "Buzz"
          case (_, _) => x.toString()
        }
        fizzBuzz(x + 1)
      }
      //endsolve
      
      //test
      fizzBuzz(12) should be("Fizz")
      fizzBuzz(20) should be("Buzz")
      fizzBuzz(15) should be("FizzBuzz")
      fizzBuzz(13) should be("13")
      
      //endtest
    }
  }

  video("Try/catch statements", "XlAA6jfy82o")
  koan(""" Exception handling:
    Like in Java, Scala offers a way to handle exceptions. The try/catch expression uses pattern matching like the match expression.
    With this you can easily match the exception and handle it the right way. The possibility of using a wildcard in a try/catch isn't
    good practice and even the compile will warn you about this.""") {
    val response = try {
      val s = "Hello World"
      s.charAt(15)
    } catch {
      case number: java.lang.NumberFormatException => "You entered an illegal number"
      case bounds: java.lang.StringIndexOutOfBoundsException => "You tried to access a character that is not in range of the String"
      case _ => "You cought everything, well done!!!"
    }
    response should be("You tried to access a character that is not in range of the String")
  }
}
