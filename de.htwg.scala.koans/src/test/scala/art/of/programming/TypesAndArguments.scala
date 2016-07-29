import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class TypesAndArguments extends CodeTaskSuite("Types and Arguments", 6) {
  koan(""" Option Type:
    The option type is inteded to represent something that may or may not have a value.
    It is used by the type system to help you find certain type of errors. E.g. if something
    isn't supposed to have a value the type system will force you to deal with it.""") {
    val lst = List(1, 2, 3, 4, 5)
    lst.find(_ > 6) should be(None)

    lst.find(_ > 3) should be(Some(4))
    lst.find(_ > 6).get should be("java.util.NoSuchElementException")

    lst.find(_ > 6).getOrElse(-1) should be(-1)
    lst.find(_ > 6).map(_ * 2) should be(None)
    lst.find(_ > 3).map(_ * 2) should be(Some(8))

    val response = lst.find(_ > 3) match {
      case Some(n) => n * 5
      case None    => -1
    }
    response should be(20)

    val result = lst.find(_ > 3)
    val ifResponse = if (result.nonEmpty) result.get * 5 else -1
    ifResponse should be(20)
  }

  koan(""" Parametric Functions:
    Parametric functions are function which take types as arguments. In Java those are called Generics. Parametric
    functions are not the same as generics but for a mental model you can treat them as if they were.""") {
    def ident[A](x: A): A = x
    ident(2) should be(Int)
    ident(2.0) should be(Double)
    ident("Hi") should be("java.lang.String")

    def makeTuple[A, B](a: A, b: B): (A, B) = (a, b)
    makeTuple(1, 1) should be((Int, Int))
    makeTuple(1, 1.0) should be((Int, Double))
    makeTuple("Hi", 'a') should be("(java.lang.String, Char)")
  }

  koan(""" Subtyping:
    In Scala everything is a type. In the documentation of Scala each type is a subtype of another type. The main
    types in Scala are AnyVal, AnyRef and Any. Any class you could imagine is a subtype of those types. The compiler has
    to distinguish a type each time a user defines a variable. In the example the compiler has to find a type for the list entries
    Try to figure out which type the List has to be, write the result inside of two ("). """) {
    List("Hi", 5) should be("List[Any]")
    List(5, true) should be("List[AnyVal]")
    List() should be("List[Nothing]")
  }

  koan(""" Variable Argument Lists:
    These are argument lists that do not have a specified length as the name implies.
    In Java this feature was written with String[] args or since Java 6 with String... args. This idenitifed a 
    variable length of arguments
    Note: The variable type can only be the last argument in the list. This problem can be bypassed by currying the function.""") {
    def average(n: Double*) = n.sum / n.length
    average(1, 2, 3, 4, 5) should be(3.0)

    val lst = List[Double](1, 2, 3, 4, 5)

    def averageList(lst: List[Double]): Double = lst.sum / lst.length
    averageList(lst) should be(3.0)

    average(lst: _*) should be(3.0)
  }

  koan("""Mutability:
    A big concern when doing concurrent programming is mutability. Scala strives for immutability which eliminates the 
    failure of accidentally changing a value. In the example below you see whats called aliasing. Aliasing is when you 
    wanted to make a copy of something but accidently just gave it an alias. When changing the value of the alias you 
    the value of the original collection.""") {
    val a = Array(1, 2, 3, 4, 5)
    val b = a
    b(0) = 42
    a == b should be(true)

    def zeroArray(a: Array[Int], i: Int): Unit = {
      if (i < a.length) {
        a(i) = 0
        zeroArray(a, i + 1)
      }
    }
    zeroArray(a, 0)
    a should be(Array(0, 0, 0, 0, 0))
  }

  koan(""" Currying:
    Opens the possibility to use multiple argument lists. Functions that let you use currying are e.g. fill, tabulate or fold.
    One way of to make use of currying happens when you return a function.
    Currying can be very useful when using Variable Argument Lists. You are now able to use more than one variable argument list.""") {
    def add(x: Int): Int => Int = y => x + y
    add(5)(11) should be(16)

    val plus5 = add(5)
    plus5(10) should be(15)

    def addMultipleArg(x: Int)(y: Int): Int = x + y
    add(11)(31) should be(42)

    val plus6 = addMultipleArg(6)_
    plus6(12) should be(18)
  }

  koan(""" Pass by Name:
    In addition to the default passing semantic, scala uses or allows pass by name. It is mostly disallowed
    in many other programming languages. When understood and used right it can 
    give you an ability to do certain task easier. The function fill does this by default.
    
    Note: Take care when using or applying this to functions. Take note how the parameters are evaluated.""") {
    def incr(i: Int) = i + 1
    incr(3 + 4) should be(8)

    var a = 0
    def thriceMultiply(i: Int): Int = i * i * i
    thriceMultiply({ a += 1; a }) should be(1)

    a = 0
    def thriceMultiplyAdv(i: => Int): Int = i * i * i
    thriceMultiplyAdv({ a += 1; a }) should be(6)
  }

  koan(""" Multidimensional Arrays:
    Like in any other language scala offers the possibility to create multidimensial arrays which is exactly
    what the name implies within arrays. It can be archived with the library methods fill or tabulate.""") {
    val arr2Dim = Array(Array(1, 2), Array(2, 3))
    arr2Dim(0)(1) should be(2)

    Array.fill(2, 3)(0) should be(Array(Array(0, 0, 0), Array(0, 0, 0)))
    Array.tabulate(3, 3)((i, j) => i * j) should be(Array(Array(0, 0, 0), Array(0, 1, 2), Array(0, 2, 4)))
  }
}