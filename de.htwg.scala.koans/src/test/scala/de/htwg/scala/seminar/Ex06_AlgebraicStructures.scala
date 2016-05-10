package de.htwg.scala.seminar

import de.htwg.scala.koans.KoanSuite
import spire.math.Natural
import spire.implicits._
import spire.algebra._
import spire.math._
import spire.syntax.literals._
import spire.syntax.literals.radix._
import spire.syntax.literals.si._

/**
  * Created by Ron on 10.05.2016.
  */
class Ex06_AlgebraicStructures extends KoanSuite{


  koan("A rational number is precise") {
    val n1 = r"1/3"
    val n2 = 1/3
    n1 == n2 should be (false)
  }

  koan("A rational number is simplified at compile-time ") {
    val n3 = r"8/18"
    val n4 = n3.toString

    n4 should be("4/9")
  }


  koan("binary"){
    val n5 = x2"10111"
    n5.equals(23) should be (true)

    val n6 = x16"17"
    n6.equals(23) should be (true)
  }


  koan("ditibutive law"){
    3 * (2 + 4) == 3*2 + 3*4 should be (true)

    4 * (1 - 2) should be (5*1 - 5*2)
  }

  koan("commutative law"){
    3 - 2 == 2 - 3 should be (false)

    4 + 3 should be(3 + 4)
  }

  koan("associative law"){
    3 * (2 * 7) == (3 * 2) * 7 should be (true)

    2 + (3 + 6) should be ((2 + 3) + 6)
  }

  koan("inverse & neutral element"){
    object AlgebraicStructure{
      implicit object OwnStructure extends Group[Int]{
        def op(x: Int, y: Int): Int = x + y
        def inverse(x: Int): Int = -x
        def id: Int = 0

      }
    }

    AlgebraicStructure.OwnStructure.op(4,AlgebraicStructure.OwnStructure.id) should be (4)

    AlgebraicStructure.OwnStructure.op(4, AlgebraicStructure.OwnStructure.inverse(4)) should be (AlgebraicStructure.OwnStructure.id)

  }



  koan("TimeMonoid"){
    class Time(val hours: Int, val minutes: Int){
    }

    implicit object TimeStructure extends Semiring[Time] {
      def plus(x: Time, y: Time): Time = new Time(calcHours(x,y), (x.minutes + y.minutes) % 60)
      def one: Time = new Time(1, 2)
      def negate(x: Time) = new Time(1, 2)

      def times(x: Time, y: Time): Time = new Time(1, 2)

      def zero: Time = new Time(1, 2)

      def calcHours(x:Time, y: Time): Int = if(x.minutes + y.minutes < 60) {
        (x.hours + y.hours) % 12
      }
      else{
        (x.hours + y.hours + 1) % 12
      }
    }

    val time = TimeStructure.plus(new Time(11,50),new Time(4,20))
  time.hours should be (4)
  time.minutes should be (10)
  }

  koan("TypeClass"){

  }

}
