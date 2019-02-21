package de.htwg.math

import org.codetask.koanlib.CodeTaskSuite
import spire.math._
import spire.implicits._

class ComplexNumbers extends CodeTaskSuite("Complex Number",1) {

  koan("The Scala library Spire provides a type for complex numbers. They consist of a real and an imaginary part. ") {
    val ci1 = Complex(2, 1)
    ci1.real should be(2)
    ci1.imag should be(1)
  }

  koan("Complex numbers have a textual representation using the constant i like (3 + 4i)") {
    val ci1 = Complex(5, 6)
    ci1.toString() should be("(5 + 6i)")
  }
  
  koan("Complex numbers can be created from their textual representation") {
    val i = Complex.i[Double] 
    Complex(0,1) === i should be(true)
  }
  
  koan("Complex numbers can be created from a textual representation, for convenience you should then define i as local variable") {
    val c1=(9.0 -2.5*Complex.i[Double])
    c1.real should be(9.0)
    
    val i = Complex.i[Double] 
    val c2 = (9.0 - 2.5*i)
    c2.imag should be(-2.5)
  }

  koan("Complex numbers can be added and subtracted") {
    val ci1 = Complex(2, 1)
    val ci2 = Complex(3, 5) 
    val ci3 = ci1 + ci2
    ci3.real should be(5)
    ci3.imag should be(6)
    
    (ci2 - ci1).real should be(1)
  }

  koan("The real and the imaginary part can each be of type Int or Double (or even other number types)") {
    val cd1 = Complex(2.0, 1.0)
    val cd2 = Complex(3.3, 4.6)
    val cd3 = cd1 + cd2
    cd3.toString should be ("(5.3 + 5.6i)")
  }
  
  koan("Complex numbers have equivalence") {
    val ci1 = Complex(2, 1)
    val ci2 = Complex(3, 5)
    ci1 === ci2 should be(false)
    ci1 =!= ci2 should be(true)
  }
  
  koan("Trigonometric functions can operate on complex numbers") {
    val cd1 = Complex(2.0, 0.0)
    cd1.sin.imag should be(0)
  }

}
