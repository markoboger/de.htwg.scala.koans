package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class Loops extends CodeTaskSuite("Loops", 7) {
  koan(""" While loop:
    Instead of using recursion or higher order methods, scala gives you the ability to write a loop
    which you can use to iterate over something. The while loop is a lesser used form of loop. 
    This is because in scala the while loop is a statement not an expression. It does return Unit but 
    you want to avoid the Type Unit in Scala.""") {
    var i = 0
    var sum = 0
    while (i < 10) {
      sum += 1
      i += 1
    }
    sum should be(10)
  }

  koan(""" Do while loop:
    The do while loop is similar to the while loop. The only distinction from the while loop is that 
    the loop will execute once at minimum because of the trailing conditional. The do while loop is the
    least used form of loop.""") {
    var i = 0
    var sum = 0
    do {
      sum += 1
      i += 1
    } while (i < 10)
    sum should be(10)
  }

  koan(""" Ranges: 
    Commonly used forms of loops create a variable which will be incremented/decremented till it surpasses
    a certain bar, limit or something else. For this general occuring event, scala introduced something called 
    range. Ranges are collections and thereby offer all the rich methods of the scala collection. Ranges can be
    iterated and used in loops.
    
    Try writing down the right types of the first 5 statements and the actual values of the last two.""") {
//    0 to 9 should be("scala.collection.immutable.Range")
    0 until 9 should be("scala.collection.immutable.Range")
    Array(1, 2, 3).indices should be("scala.collection.immutable.Range")
    'a' to 'z' should be("scala.collection.immutable.NumericRange.Inclusive[Char]")
    1.0 to 2.0 by 0.5 should be("scala.collection.immutable.NumericRange[Double]")

    10 to 1 should be("Range()")
    10 to 1 by -1 should be("Range(10,9,8,7,6,5,4,3,2,1)")
  }

  codetask(""" Exercise: (Simple for loop)
    Write a simple for loop that prints each element of the supported array.""") {
    val a = Array(1, 2, 3, 4, 5)
    //solve
    for (i <- a) println(i)
    //endsolve
  }

  koan(""" For loop: 
    The main form of loop is the for loop. They are less error prone then while loops within doing the same work.
    For loops can be structured with using Ranges as seen in the example below. Moreover the example below shows
    how to simplify and gain more readability when using the right functions for the job.
    
    Note: Polynom = coeff(0)x^2+coeff(1)x^1+coeff(2)x^0.""") {
    val a = Array(3.0, 2.0, -5.0)
    def evalPolynomBad(coeffs: Array[Double], x: Double): Double = {
      var sum = 0.0
      for (i <- coeffs.indices) {
        sum += coeffs(i) * Math.pow(x, coeffs.length - 1 - i)
      }
      sum
    }
    evalPolynomBad(a, 1.0) should be(0.0)

    def evalPolynomBetter(coeffs: Array[Double], x: Double): Double = {
      var sum = 0.0
      var power = 1.0
      for (c <- coeffs.reverse) {
        sum += c * power
        power *= x
      }
      sum
    }
    evalPolynomBetter(a, 2.0) should be(11.0)
  }

  koan(""" Yield:
    Till this point we used for loops as statements not expressions. For this exact moment Scala introduced 
    the keyword yield. In previous examples we had to write several var declarations to make things work.
    To prevent the use of var we can now easily add the keyword yield which will give us something back.""") {
    val response = for (i <- 1 to 10) yield i
    response should be(Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

    val a = Array(3.0, 2.0, -5.0)
    def evalPolyBest(coeffs: Array[Double], x: Double): Double = {
      (for (i <- coeffs.indices) yield {
        coeffs(i) * Math.pow(x, coeffs.length - 1 - i)
      }).sum
    }
    evalPolyBest(a, 3.0) should be(28.0)
  }

  koan(""" Yield If Guards:
    In addition to conditionals seen in the chapter about conditionals you can use if guards in loops. 
    This could be done especially when applying the yield to the for loop.
    
    Note: Keep in mind how often the passed function is evaluated.""") {
    val wrongResponse = for (i <- 1 to 10) yield { if (i % 2 == 0) i * i }
    wrongResponse should be(Vector((), 4, (), 16, (), 36, (), 64, (), 100))

    val rightResponse = for (i <- 1 to 10; if i % 2 == 0) yield i * i
    rightResponse should be(Vector(4, 16, 36, 64, 100))
  }

  koan(""" Multiple generators: 
    Multiple generators could be described as a nested loop. With adding additional ranges into the 
    for loop you gain the ability to create or run multidimensional collections.""") {
    val response = for (i <- 1 to 2; j <- 1 to 2) yield (i, j)
    response should be(Vector((1, 1), (1, 2), (2, 1), (2, 2)))

    val responseWithProduct = for (i <- 1 to 2; j <- 1 to 2) yield (i, j, i * j)
    responseWithProduct should be(Vector((1, 1, 1), (1, 2, 2), (2, 1, 2), (2, 2, 4)))
  }

  codetask(""" Exercise: (Pattern matching with yield)
    Write a statement which uses Pattern matching, for loop and yield to concatenate the elements of a given two tuple with the 
    String ("_"). The result should an array with the concatenated names.""") {
    val lst = List(("Max", "Mustermann"), ("Hans", "Werner"), ("Klaus", "Mann"))

    // solve
    val response = for ((firstname, lastname) <- lst) yield firstname + "_" + lastname
    // endsolve

    // test
    response should be(List("Max_Mustermann", "Hans_Werner", "Klaus_Mann"))
    // endtest
  }

  koan(""" Variable declaration in for loops:
    Another benefit for the for loop construct is that you can declare variables into the loop header. This benefits the readability
    and maintainability of the codebase.""") {
    val grid = for (x <- 0 to 2; y <- 2 to 0 by -1) yield (x, y)
    grid should be(Vector((0, 2), (0, 1), (0, 0), (1, 2), (1, 1), (1, 0), (2, 2), (2, 1), (2, 0)))

    val findMax = for ((x, y) <- grid; winner = scala.math.max(x, y)) yield (winner)
    findMax should be(Vector(2, 1, 0, 2, 1, 1, 2, 2, 2))
  }

  koan(""" For Comprehension:
    The for loop as we know isn't a real for loop in a sense that we know from other languages.
    In Scala the for loop is internally constructed with higher order methods like map, filter, reduce and foreach. 
    The idea behind the loop is to offer an easy way to write a loop without using the mentioned methods directly. This
    has the benefit of constructing loops without having to write lambda literals or clumpsy syntax. On the second hand it does provide
    a more readable codebase.""") {
    for (i <- 1 to 10) println(i)
    (1 to 10).foreach(i => println(i))

    for (i <- 1 to 10) yield i * i should be(Vector(1, 4, 9, 16, 25, 36, 49, 64, 81, 100))
    (1 to 10).map(i => i * i) should be(Vector(1, 4, 9, 16, 25, 36, 49, 64, 81, 100))

    for (i <- 1 to 10; if i % 2 == 0) yield i * i should be(Vector(4, 16, 36, 64, 100))
    (1 to 10).filter(_ % 2 == 0).map(i => i * i) should be(Vector(4, 16, 36, 64, 100))
  }

  koan(""" Multidimensial loop: 
    Besides mutliple generators you can also construct loops with explicit nesting of for loops as seen in the example 
    below.""") {
    val response = for (i <- 1 to 3) yield {
      for (j <- 1 to 3) yield i * j
    }

    response should be(Vector(Vector(1, 2, 3), Vector(2, 4, 6), Vector(3, 6, 9)))
  }

  koan(""" Parallel for loops:
    Due to default technologie standards these days every computer has at least two cores. To make use of these ressources,
    programs have to be written for parallel or concurrent execution. In Scala this is easily archived by calling the method
    par on collections.
    Try to figure out the type of the first statement and afterwards the result of the for loop which is run in parallel.""") {
    (1 to 10).par should be(scala.collection.parallel.immutable.ParRange)

    var i = 0
    for (j <- (1 to 1000000000).par) i += 1
    i == 1000000000 should be(false)
  }

  koan(""" Views:
    Views are an performance enhancement feature given by Scala. Each time higher order methods like map, filter or reduce are called scala will create
    a completly new collection. If you do this multiple times you will consume a lot of memory each time. The view concept encapsulates the collection with
    a wrapper type which is less memory consuming than creating new collections.
    Try to figure out the type of the following statements.""") {

    val numbers = Array.fill(100)(Math.random)
    numbers should be("Array[Double]")
    numbers.map(x => x * x).filter(_ < 0.25) should be("Array[Double]")
    numbers.view.map(x => x * x).filter(_ < 0.25) should be("scala.collection.SeqView[Double,Array[Double]]")
  }
}