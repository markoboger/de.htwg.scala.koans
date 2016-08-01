package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class Basics extends CodeTaskSuite("Basics", 1) {

  koan("""Welcome to this course on the Art of Programming using Scala. 
It is based on a book with the same title by Mark C. Lewis and a set of videos that accompany the book. 
This course combines the videos by Mark C. Lewis with interactive code snippets, called koans. 
You should listen to the video, then fill in the blanks in the code that follows.
For some chapters we also provide little programming exercises we call codetasks. 
Here you need to fill in a few lines of code to pass a test. Let's get started: """) {
    2 + 3 should be(5)
  }

  video("Objects and associated methods", "OdHJBxfOIfs")

  koan("""Calls on Integers: Try out some basic operations on Integers""") {
    5.6.round should be(6)
    5.6.toInt should be(5)

    4.+(5) should be(9)
    3 + 8 should be(11)

  }

  video("Scala Types", "yrpQnVGMX1g")

  koan("""Primitiv Datatypes: 
    Scala has a set of primitve datatypes which will 
    be familiar from other programming languages like 
    Java, C++ and so on. The difference in Scala is 
    that all of those datatypes are implemented as Classes.
    
    Solve the type of the following values""") {
    5.isInstanceOf[Int] should be(true)
    (5.1).isInstanceOf[Double] should be(true)
    (5.1F).isInstanceOf[Float] should be(true)
    5000L.isInstanceOf[Long] should be(true)
    'x'.isInstanceOf[Char] should be(true)
    true.isInstanceOf[Boolean] should be(true)
    "Hello".isInstanceOf[String] should be(true)
  }

  koan("""Tuple:
    Scala is a functional language and in this case tuples likely 
    known from math are mandatory. Especially for the use in the match statement. 
    This will be discussed or tested in a later state of tests.
    
    Solve the right representation for the following tuples""") {
    ("Hello", 42).isInstanceOf[(String, Int)] should be(true)
    ("Hello" -> 42).isInstanceOf[(String, Int)] should be(true)

    ("Hello", 42, 'a').isInstanceOf[(String, Int, Char)] should be(true)
    ("Hello" -> 42 -> 'a').isInstanceOf[(String, Int, Char)] should be(false)
    ("Hello" -> 42 -> 'a').isInstanceOf[((String, Int), Char)] should be(true)
  }

  koan("""Tuple access:
    Accessing the values in a tuple can be archived in different ways.
    In this test case we use the primitive notation by accessing the variables
    directly with indices.
    """) {
    ("Hello", 42)._1 should be("Hello")
    ("Hello", 42)._2 should be(42)
    ("Hello" -> 42 -> 'a')._2 should be('a')

    ("Hello" -> 42 -> 'a')._1 should be("Hello" -> 42)
    ("Hello" -> 42 -> 'a')._1._2 should be(42)
  }

  koan("""Primitive datatype arithmetic:
    Each primitive datatype has a unique set of arithmetic functions like +,* implemented.
    These methods can be called by invoking the method on the value of which you'll be invoking
    the method. The argument on the right side will be used as the argument. 
    In Scala there is a special case in which you could leave the default method invokation symbol (symbol =  '.' ) 
    if the is only one arguments on the right side.
    
    Solve the following values""") {
    5.6.+(4.4) should be(10.0)
    5.6 + 4.4 should be(10.0)

    'a' + 1 should be('b')

    "Hello " + "World" should be("Hello World")
    "Hello " + 5.6 should be("Hello 5.6")
    "Hello " * 3 should be("Hello Hello Hello ")
  }

  video("Additional Integer Types", "dJPxiFFZypU")

  koan("""Casting:
    Like in every programming language you can cast a given value with a certain type
    to a similiar type. For example double to int. In Scala each primitive datatype has a
    method for each other datatype.
    """) {
    5.6.toInt should be(5)

    42.5.toInt should be(42)
  }

  video("Details of numbers", "2dImF4DCWlY")

  koan("""Type Limits:
    Types have a certain amount of bytes reserved for their value. Due to this restriction 
    there is a chance to get overflows during runtime.
    
    Solve the right value for the following statements""") {
    1000000000 + 1000000000 should be(2000000000)
    2000000000 + 2000000000 should be(-294967296)

    Int.MaxValue should be(2147483647)
    Int.MinValue should be(-2147483648)

    Char.MaxValue.toInt should be(65535)
    Char.MinValue.toInt should be(0)
  }

  video("Binary Arithmetic and presentation", "212jDl_5B7E")
  video("Signed and unsigned values", "qTAw30GgDZs")

  koan("""Value presentation:
    
    """) {
    42.toBinaryString should be("101010")
    42.toOctalString should be("52")
    42.toHexString should be("2a")
    -42.toBinaryString should be("11111111111111111111111111010110")
  }

  video("Floating point numbers and math libary", "LrU7mLiTWKo")

  koan(""" Mathematical Functions:
    Scala delivers a special Math Library like many other programming languages.
    Since version 2.08 Scala refactored their math library. It is now accessable
    via "scala.math"
    Solve the result of the constants and method calls""") {
    scala.math.Pi should be(3.141592653589793)
    scala.math.E should be(2.718281828459045)
    scala.math.sqrt(9) should be(3.0)
  }

  video("Mental model of variables", "TLfRT5Ab7d0")
  video("Values and Variables", "xJNb8UhrMcY")

  koan(""" Variables and Pattern matching:
    There are two variable concepts in Scala, Val and Var. Val declares a constant/immutable variable.
    Var on the other hand declares a mutable variable. Due to best practice the usa of Var should
    be minimized to the bare minimum.
    
    Pattern matching revisits the problem of accessing a tuple. It provides an elegant way of accessing
    the values of a tuple by simple giving them temporary names instead. This feature makes the code 
    more readable which empowers the level of understanding.""") {
    val constValue = 5
    var variableValue = 6

    val explicitInt: Int = 6

    val name = ("Max", "Mustermann")
    name._1 should be("Max")

    val (firstName, lastName) = ("Max", "Mustermann")
    firstName should be("Max")
    lastName should be("Mustermann")
  }

  video("Details of Strings and Chars", "hkPwD4HUK_0")

  koan(""" String Interpolation:
    Scala delivers a special string interpolation operator (s") which offers the chance
    to concat strings without having the problem of escaping characters. The syntax is 
    very similar to Printf methods where you add a placeholder for the value. Using s" is bound to
    strings. If you want to be more flexible you can use the (f") interpolator. With this you have
    exactly the same style as with printf. 
    
    There is a second method of writing strings without bothering about escaping characters
    and this could be archived with tree ("), see last statement.""") {
    val a = 5
    val b = 10
    val c = 'x'

    a + " " + b + " " + c should be("5 10 x")
    s"$a $b $c" should be("5 10 x")
    s"${a + 5} $b $c" should be("10 10 x")
  }

  video("Basic string methods", "DDlVziE243A")
  video("Immutability of Strings", "jZOg0P8-26E")
  video("Sequential Execution and string example", "ReS2nusS7R8")

  koan(""" String Methods:
    Due to the fact that Scala is using the String implementation of Java all methods are the same.
    If you're familiar with Java and the String Class then you know them already.""") {
    val name = "Max"
    name.length should be(3)

    name(0) should be('M')

    name.indexOf("M") should be(0)
    name.indexOf("Z") should be(-1)

    val fullName = "Max Mustermann"
    fullName.substring(4) should be("Mustermann")
    fullName.substring(4, 10) should be("Muster")

    fullName.splitAt(4) should be(("Max ", "Mustermann"))

    "Max ".trim should be("Max")
    "Max".toUpperCase should be("MAX")
    "Max".toLowerCase should be("max")
  }
}