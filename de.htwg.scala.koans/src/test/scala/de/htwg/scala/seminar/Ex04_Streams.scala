package test.scala.de.htwg.scala.seminar

import de.htwg.scala.koans.KoanSuite

class Ex04_Streams extends KoanSuite {

  val answer1 = Stream.empty
  val answer2 = Stream(1, 2, 3)
  val answer3 = Stream.range(100, 250, 10)

  val answer5 = 1 #:: 2 #:: 3 #:: Stream.empty.force
  val answer6 = (1 #:: 2 #:: 3 #:: Stream.empty)(1)
  val answer7 = 1
  val answer8 = 2 to 3
  var answer9 = 0
  var answer10 = ""
  var answer11 = ""
  var answer12 = ""
  var answer13 = ""
  var answer14 = ""
  var answer15 = ""
  val answer16 = "yes"
  val answer17 = "yes"
  val answer18 = "yes"
  val answer19 = "no"
  val answer20 = "no"
  val answer21 = "no"
  val answer22 = "no"
  val answer23 = "no"
  val answer24 = "no"
  val answer25 = "no"
  val answer26 = "yes"
  val answer27 = 3
  val answer28 = 0
  val answer29 = 4
  val answer30 = 8
  val answer31 = 1
  val answer32 = "no"
  val answer33 = "no"
  val answer34 = "yes"
  val answer35 = "Stream.continually(5)"
  val answer36 = "Stream.from(10)"
  val answer37 = "Stream.from(100, 10)"

  val answer39 = Stream.from(100, 10)
  val answer40 = "yes"
  var answer41 = ""
  var answer42 = ""
  var answer43 = ""
  val answer44 = "yes"


  koan("1. Create a empty Stream.") {

    answer1 should be(Stream.empty)
  }

  koan("2. Create an Int-Stream with 1, 2, 3. Use only right associative.") {

    answer2 should be(1 #:: 2 #:: 3 #:: Stream.empty)
  }

  koan("3. Create an Int-Stream range start: 100, end: 250 and step range 10.") {

    answer3 should be(Stream.range(100, 250, 10))
  }

  koan("4. Concatenate of two Streams") {

    val stream1 = 1 #:: 2 #:: 3 #:: Stream.empty
    val stream2 = 4 #:: 5 #:: 6 #:: Stream.empty

    val answer4 = stream1 append stream2

    answer4.force should be(Stream(1, 2, 3, 4, 5, 6))
  }

  koan("5. Get whole Stream") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty

    answer5 should be(stream.force)
  }

  koan("6. Get second entity of Stream") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty

    answer6 should be(stream(1))
  }

  koan("7. Get head of Stream") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty

    answer7 should be(stream.head)
  }

  koan("8. Get tail of Stream") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty

    answer8.toStream should be(stream.tail)
  }

  koan("9. What's the result of head") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty
    answer9 = stream.head

    answer9 should be(1)
  }

  koan("10. What's the result of tail, as string") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty
    answer10 = stream.tail.toString()

    answer10 should be("Stream(2, ?)")
  }

  koan("11. What's the result of tail after force(), as string") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty
    stream.force
    answer11 = stream.tail.toString()

    answer11 should be("Stream(2, 3)")
  }

  // Exceptions ********************************************************************************************************
  koan("12: There is an exception thrown, \"yes\" or \"no\"?") {

    try {
      val listExc = 1 :: 2 :: 3 :: (throw new Exception("END OF LIST!")) :: List.empty
      answer12 = "no"
    } catch {
      case _: Exception =>
        answer12 = "yes"
    }

    answer12 should be("yes")
  }

  koan("13: There is an exception thrown, \"yes\" or \"no\"?") {

    try {
      val streamExc = 1 #:: 2 #:: 3 #:: (throw new Exception("END OF Stream!")) #:: Stream.empty
      answer13 = "no"
    } catch {
      case _: Exception =>
        answer13 = "yes"
    }

    answer13 should be("no")
  }

  koan("14: There is an exception thrown, \"yes\" or \"no\"?") {

    try {
      val streamExc = 1 #:: 2 #:: 3 #:: (throw new Exception("END OF Stream!")) #:: Stream.empty
      streamExc.take(3).force
      answer14 = "no"
    } catch {
      case _: Exception =>
        answer14 = "yes"
    }

    answer14 should be("no")
  }

  koan("15: There is an exception thrown, \"yes\" or \"no\"?") {

    try {
      val streamExc = 1 #:: 2 #:: 3 #:: (throw new Exception("END OF Stream!")) #:: Stream.empty
      streamExc.take(4).force
      answer15 = "no"
    } catch {
      case _: Exception =>
        answer15 = "yes"
    }

    answer15 should be("yes")
  }


  // Lazy operations **************************************************************************************************
  koan("16: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.filter(_ < 200)

    answer16 should be("yes")
  }

  koan("17: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.filter(_ > 200)

    answer17 should be("yes")
  }

  koan("18: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.map {
      _ * 2
    }

    answer18 should be("yes")
  }

  koan("19: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.sum

    answer19 should be("no")
  }

  koan("20: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.size

    answer20 should be("no")
  }

  koan("21: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.find(_ == 400)

    answer21 should be("no")
  }

  koan("22: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.max

    answer22 should be("no")
  }

  koan("23: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.foldLeft(0)((b, a) => b + a)

    answer23 should be("no")
  }

  koan("24: This is a lazy operation, \"yes\" or \"no\"?") {

    val stream = (1 to 1000).toStream
    stream.exists(_ == 1400)

    answer24 should be("no")
  }


  // Memory consumption ************************************************************************************************
  koan("25. Is the memory consumption low, \"yes\" or \"no\"?") {

    def printSteps(s: String, i: Int, iter: Iterator[Int]): Unit = {
      // Stop after 200,000
      if (i < 200001) {
        if (i % 50000 == 0) println(s + i)
        printSteps(s, iter.next(), iter)
      }
    }

    val stream10: Stream[Int] = {
      def loop(v: Int): Stream[Int] = {
        v #:: loop(v + 1)
      }
      loop(0)
    }

    val it1 = stream10.iterator
    printSteps("Iterator1: ", it1.next(), it1)

    answer25 should be("no")
  }

  koan("26. Is the memery consumption low, \"yes\" or \"no\"?") {

    def printSteps(s: String, i: Int, iter: Iterator[Int]): Unit = {
      // Stop after 200,000
      if (i < 200001) {
        if (i % 50000 == 0) println(s + i)
        printSteps(s, iter.next(), iter)
      }
    }

    def stream20: Stream[Int] = {
      def loop(v: Int): Stream[Int] = v #:: loop(v + 1)
      loop(0)
    }

    val it2 = stream20.iterator
    printSteps("Iterator2: ", it2.next(), it2)

    answer26 should be("yes")
  }

  // evaluation ********************************************************************************************************
  koan("27. How often is \"Random: ...\" called when \"randStream(3)\" is evaluated?") {

    def makeRand: Stream[Int] = {
      Stream.cons(
      {
        val temp = util.Random.nextInt(1000)
        println(s"Random: $temp")
        temp
      }, {
        makeRand
      }
      )
    }
    val randStream = makeRand

    randStream(3)

    answer27 should be(3)
  }


  koan("28. How often is \"Random: ...\" called when \"randStream(1)\" is evaluated?") {

    def makeRand: Stream[Int] = {
      Stream.cons(
      {
        val temp = util.Random.nextInt(1000)
        println(s"Random: $temp")
        temp
      }, {
        makeRand
      }
      )
    }
    val randStream = makeRand

    randStream(3)
    randStream(1)

    answer28 should be(0)
  }

  koan("29. How often is \"Random: ...\" called when \"randStream(7)\" is evaluated?") {

    def makeRand: Stream[Int] = {
      Stream.cons(
      {
        val temp = util.Random.nextInt(1000)
        println(s"Random: $temp")
        temp
      }, {
        makeRand
      }
      )
    }
    val randStream = makeRand

    randStream(3)
    randStream(7)

    answer29 should be(4)
  }

  koan("30. How often is \"Random: ...\" called in total?") {

    def makeRand: Stream[Int] = {
      Stream.cons(
      {
        val temp = util.Random.nextInt(1000)
        println(s"Random: $temp")
        temp
      }, {
        makeRand
      }
      )
    }
    val randStream = makeRand

    randStream(7)

    answer30 should be(8)
  }

  koan("31. How often is \"Random: ...\" called in total?") {

    def makeRand: Stream[Int] = {
      Stream.cons(
      {
        val temp = util.Random.nextInt(1000)
        println(s"Random: $temp")
        temp
      }, {
        makeRand
      }
      )
    }
    val randStream = makeRand

    answer31 should be(1)
  }

  koan("32. This forces evaluation , \"yes\" or \"no\"?") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty
    val sb = new StringBuilder

    stream.addString(sb)

    answer32 should be("no")
  }

  koan("33. This forces evaluation , \"yes\" or \"no\"?") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty

    stream.toString()

    answer33 should be("no")
  }

  koan("34. This forces evaluation , \"yes\" or \"no\"?") {

    val stream = 1 #:: 2 #:: 3 #:: Stream.empty

    stream.mkString

    answer34 should be("yes")
  }


  //  // create streams ****************************************************************************************************
  koan("35. Create an infinite Int-Stream with 5.") {

    answer35 should be("Stream.continually(5)")
  }

  koan("36. Create an infinite Int-Stream, start with 10 inc 1.") {

    answer36 should be("Stream.from(10)")
  }

  koan("37. Create an infinite Int-Stream start with 100 and step range 10.") {

    answer37 should be("Stream.from(100, 10)")
  }

  koan("38. Create an Int-Stream with 1, 2, 3. Use cons.") {

    val answer38 = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty)))

    answer38.force should be(Stream(1, 2, 3))
  }

  koan("39. Create an Int-Stream with 1, 2, 3. Use brackets \"(...)\" with ConsWrapper function.") {

    val answer39 = 1 #:: (2 #:: (3 #:: Stream.empty))

    answer39.force should be(Stream(1, 2, 3))
  }

  koan("40. This is an infinite Int-Stream cycle with 1, 2, 3, \"yes\" or \"no\"?") {
    "var s: Stream[Int] = 1 #:: 2 #:: 3 #:: s"

    answer40 should be("yes")
  }


  //  // Pattern Matching **************************************************************************************************
  koan("41. Use Pattern-Matching to get Streams which start with 3, 5. Is this correct \"yes\" or \"no\"?") {
    val stream = 3 #:: 5 #:: 7 #:: Stream.empty

    stream match {
      case _ =>
        answer41 = "no"
      case 3 #:: 5 #:: _ =>
        answer41 = "yes"
    }

    answer41 should be("no")
  }

  koan("42. Use Pattern-Matching to get Streams which start with 3, 5. Is this correct \"yes\" or \"no\"?") {
    val stream = 3 #:: 5 #:: 7 #:: Stream.empty

    stream match {
      case 3 #:: 5 #:: _ =>
        answer42 = "yes"
      case _ =>
        answer42 = "no"

    }

    answer42 should be("yes")
  }

  koan("43. Use Pattern-Matching to get Streams which start with 3, 5. Is this correct \"yes\" or \"no\"?") {
    val stream = 3 #:: 5 #:: 7 #:: Stream.empty

    stream match {
      case 1 #:: 5 #:: _ =>
        answer43 = "yes"
      case _ =>
        answer43 = "no"

    }

    answer43 should be("no")
  }


  // own implementation of a function **********************************************************************************
  koan("44. This code should create a range function which get an Int \"start\" parameter, an Int \"end\" parameter and creates a Stream that increments 5 each time.\n" +
    "Is this function correct, \"yes\" or \"no\"?") {

    def from(start: Int, end: Int): Stream[Int] = {
      if (start < end)
        start #:: from(start + 5, end)
      else
        Stream.empty
    }

    answer44 should be("yes")
  }

}
