package de.htwg.scala.seminar

import com.codetask.koanlib.CodeTaskSuite
import scala.util.{Try, Success, Failure}

class Ex05_MonadicStructures extends CodeTaskSuite("Monadic Structures",5) {

  // First lets start with map and flatMap
  koan("map() is an important function when it comes to monads. This koan should recall your map() knowledge.") {
    val list = List(1, 2, 3)
    list.map(_ * 2) should be (List(2, 4, 6))
  }

  koan("flatMap() is the other important function for a monadic structure. This koan should recall your flatMap() knowledge.") {
    val list = List(List(1, 2), List(3, 4))
    list.flatMap(i => i) should be (List(1, 2, 3, 4))
  }

  // This part focuses on the for-comprehension
  koan("A For-Comprehension is a monadic construct. It helps to work with monads in a simple and easy understandable" +
    " way. It's not necessary but syntactic sugar. A for-comprehension can be converted to a nested map()/flatMap()" +
    " call. This concentrates on the translation of a for-comprehension with depth 0") {
    val first = List(1, 2)
    val l1 = for { f <- first} yield f * 2
    val l2 = first.map(_ * 2)

    (l1 == l2) should be (true)
  }

  koan("Now let's move on to a for-comprehension with depth 1") {
    val first = List(1, 2)
    val second = List(3, 4)

    val l1 = for{
      f <- first
      s <- second } yield f * s

    val l2 = first.map(f =>
      second.map(s => f*s))

    val l3 = first.flatMap(f =>
      second.map(s => f*s))

    (l1 == l2) should be (false)
    (l1 == l3) should be (true)
    l1 should be (List(3, 4, 6, 8))
    l2 should be (List(List(3, 4), List(6, 8)))
  }

  koan("Finally a For-Comprehension with depth 2") {
    val first = List(1, 2)
    val second = List(3, 4)
    val third = List(5, 6)

    val l1 = for{
      f <- first
      s <- second
      t <- third } yield f * s * t

    val l2 = first.flatMap(f =>
      second.flatMap(s =>
        third.map(t => f * s * t)))

    val l3 = first.flatMap(f =>
      second.map(s =>
        third.map(t => f * s * t)))

    (l1 == l2) should be (true)
    (l1 == l3) should be (false)
  }


  // The following part focuses on the Option monad
  koan("Introduction to the option monad. The option may have a value(Some) or may not(None). This is a simple" +
    " example to understand the Relationship between Option, Some and None") {
    val i1 = Option(10)
    val i2 = Some(10)

    val n1 = Option(null)
    val n2 = None

    (i1 == n1) should be (false)
    (i1 == i2) should be (true)
    (n1 == n2) should be (true)
  }

  koan("This koan shows a pitfall that occurs with Some(null). Don't use Some(null)!") {
    val n1 = Option(null)
    val n2 = Some(null) // Try to avoid this case!

    (n1 == n2) should be (false)
    n1 should be (None)
    n2 should be (Some(null))
    n2.map(n => {
      val thrown = the [NullPointerException] thrownBy n.toString
      thrown.getClass should be(classOf[NullPointerException])
    })
  }

  koan("There are several ways to interact with an Option. Probably the first one is with get()." +
    "Get() isn't the best way to interact with Options. This examples shows why.") {
    val s1 = Option("Hello World")
    val s2 = Option(null)

    s1.get should be ("Hello World")
    s2.getOrElse("") should be ("")
    val thrown = the [NoSuchElementException] thrownBy s2.get
    thrown.getClass should be(classOf[NoSuchElementException])
  }

  koan("This example shows how to interact on Options with map(). Map() is the best way to interact with" +
    " monads. It also makes clear that the Option monad is a wrapper.") {
    val s1 = Option("Hello World")
    val s2 = Option(null)

    s1.toString() should be ("Some(Hello World)")
    s2.toString should be ("None")
    s1.map(s => s.toString should be ("Hello World"))
    s2.map(s => s.toString should be (""))
  }
  // The last part focuses on the Try monad

  koan("Introduction to the Try monad. If you're able to use the Option monad this will be no big" +
    " deal for you. The Try monad works similar to the Option monad. It's goal is to catch thrown " +
    "exceptions. On a Failure it returns a Failure and on success it returns a Success object that" +
    " holds the result.") {
    val t1 = Try("10".toInt)
    val t2 = Try("1o".toInt)

    t1 should be (Success(10))
    t1.get should be (10)
    t2.getClass shouldBe(classOf[Failure[_]])
  }
}

