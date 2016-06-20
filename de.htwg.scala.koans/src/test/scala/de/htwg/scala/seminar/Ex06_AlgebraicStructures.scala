package de.htwg.scala.seminar

import de.htwg.scala.koans.KoanSuite
import spire.math.Natural
import spire.math._
import spire.implicits._
import spire.algebra._
import spire.syntax.literals._
import spire.syntax.literals.radix._
import spire.syntax.literals.si._

/**
  * Created by Ron on 10.05.2016.
  */
class Ex06_AlgebraicStructures extends KoanSuite{


  koan("A rational number is precise") {
    val n1 = Rational(1,3)
    val n2 = 1/3
    n1 == n2 should be (false)
  }

  koan("A rational number is simplified at compile-time ") {
    val n3 = Rational(8,18)
    val n4 = n3.toString

  //  n4 should be(r"4/9")
  }


//  koan("binary"){
//    val n5 = x2"10111"
//    n5.equals(23) should be (true)
//
//    val n6 = x16"17"
//    n6.equals(23) should be (true)
//  }


  koan("distributive law"){
    3 * (2 + 4) == 3*2 + 3*4 should be (true)

    5 * (1 - 2) should be (5*1 - 5*2)
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

    implicit object OwnStructure extends Group[Int]{
      def op(x: Int, y: Int): Int = x + y
      def inverse(x: Int): Int = -x
      def id: Int = 0

    }


    OwnStructure.op(4,OwnStructure.id) should be (4)

    OwnStructure.inverse(5) should be(-5)

    OwnStructure.op(4, OwnStructure.inverse(4)) should be (OwnStructure.id)

  }



  koan("TimeMonoid") {
    case class Time(val hours: Int, val minutes: Int) {
    }

    implicit object TimeStructure extends Ring[Time] {
      def plus(x: Time, y: Time): Time = format(new Time(x.hours + y.hours, x.minutes + y.minutes))

      def one: Time = new Time(12,0)

      def negate(x: Time) = new Time(-x.hours, -x.minutes)

      def times(x: Time, y: Time): Time = format(new Time(x.hours * y.hours, x.minutes * y.minutes))

      def zero: Time = new Time(0, 0)

      def format(x: Time): Time = new Time((x.hours + (x.minutes / 60)) % 12, x.minutes % 60)
    }


    val time = TimeStructure.plus(new Time(11, 50), new Time(4, 20))
    time.hours should be(4)
    time.minutes should be(10)
    
    val time2 = Time(2,30) + Time(3,30)
    time2.hours should be(6)
    time2.minutes should be(0)
  }


}
