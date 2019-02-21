package de.htwg.math

import org.codetask.koanlib.CodeTaskSuite
import spire.math._
import spire.implicits._

class VectorSpaces extends CodeTaskSuite("Vector Spaces", 2) {
  
  koan("The Scala library Spire provides a type for Vector. It allows operations like addition and scalar product") {
    	val v1 = Vector(1,5,3) + Vector(2,1,-5) 
    	v1 should be(Vector(3, 6, -2))
    	
	    val v2= 4.0 *: Vector(1.0,5.0,3.0) 
	    v2 should be(Vector(4.0, 20.0, 12.0))
  }
  
}
