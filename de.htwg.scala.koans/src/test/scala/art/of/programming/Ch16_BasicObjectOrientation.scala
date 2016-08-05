package art.of.programming

import org.codetask.koanlib.CodeTaskSuite

class Ch16_BasicObjectOrientation extends CodeTaskSuite("Basic Object Orientation", 10) {
  
  video("Basics of Object-Orientation", "ILk_WBYsqec")
  koan(""" Object Orientation:
    Object Orientation is nothing new. Over the chapters we have used Object Orientation without even knowing.
  
    An easy example is: 
  
    val a = 5.  Even this simple statement creates an object. The Class is Int and we can now call methods
              on top of a by invoking them with a dot. 
    a.toString() 
      
    In Scala every value is an object. An Object has data and a set of functionality associated with it. Objects
    encapsulate those two elements. Scala is one of a few languages who combine functional and object orientated concepts.""") {
    class RationalNumber(val numerator: Int, val denominator: Int) {
      override def toString(): String = numerator + "/" + denominator
      def multiply(r: RationalNumber): RationalNumber = {
        new RationalNumber(numerator * r.numerator, denominator * r.denominator)
      }
      def *(r:RationalNumber):RationalNumber = this.multiply(r)
    }
    val r1 = new RationalNumber(2, 3)
    r1.toString should be("2/3")

    val r2 = new RationalNumber(1, 2)
    r1.multiply(r2).toString should be("2/6")
    (r1 multiply r2).toString should be("2/6")
    (r1 * r2).toString should be("2/6")
  }

  video("Visibility and Separating Interface from Implementation", "-GPTgSBB2E0")
  koan(""" Visibility and Separating Interface from Implementation:
    Classes encapsulate data and methods from the outside world. In the example
    below you can see how to setup the class that only the parameter name is directly
    accessable. In the second example the parameter from the class is assigned to 
    a datamember of the class which is stored during object creation. The data from the variable
    code and tresorCode isn't accesable at all time (encapsulated).""") {
    class Account(val name: String, private val balance: Int)
    val a = new Account("Svens Account", 20)
    a.name should be("Svens Account")
  }

  codetask(""" Exercises: (Class implementation)
    Scenario: A programmer has implemented a class Bank with a bank name and a code for the safe.
    Sadly the code of the safe has been assigned to a member of the class which was not private. Right there we
    have got an encapsulation problem. Plenty of other programmers are now using this implementation of the class. 
    Is there a possibility to manage the access to this class member without breaking the code for other programmers
    who use this class?   
    
    Try to figure out a way to limit or be able to handle the access to this member. Try using an additional
    variable named mSafeCode to manage the access.""") {
    class Bank(name: String, code: String) {
      var safeCode = code
    }

    class AdvancedBank(name: String, code: String) {
      //solve
      private var mSafeCode = code

      def safeCode = mSafeCode
      def safeCode_=(code: String) {
        mSafeCode = safeCode
      }
      //endsolve
    }
    //test
    val ab = new AdvancedBank("AB","123")
    ab.safeCode should be ("123")
    //endtest
  }

  video("Operator/method overloading", "ChyIF--50MA")
  video("Operator/method overloading", "4rbmndJrjI8")
  koan(""" Operator overloading and special methods:
    In Scala you can not only overload methods you can even overload operators like +-/*% and so on.
    This comes in very handy when implementing a custom type/class. You can define basic operations like in the
    example below an addition or subtraction of vectors. In Java this feature isn't given. You could only archive
    this by writing methods, but it wouldn't be easy to read (or not that easy as in Scala).
    
    Special methods like apply, update, unary and so on are nice features in Scala which let you handle certain features
    without writing unecessary boiler plate code. See example below.""") {
    class Vect3(val x: Double, val y: Double, val z: Double) {
      def +(v: Vect3) = new Vect3(x + v.x, y + v.y, z + v.z)
      def -(v: Vect3) = new Vect3(x - v.x, y - v.y, z - v.z)
      def unary_-() = new Vect3(-x, -y, -z)
      def apply(index: Int): Double = index match {
        case 0 => x
        case 1 => y
        case 2 => z
      }

      def print(): String = "(" + x + ", " + y + ", " + z + ")"
    }

    val v1 = new Vect3(1, 2, 3)
    val v2 = new Vect3(3, 2, 1)

    val addResult = v1 + v2
    addResult.print should be("(4.0, 4.0, 4.0)")
    val subResult = v1 - v2
    subResult.print should be("(-2.0, 0.0, 2.0)")
    val inversionResult = -v1
    inversionResult.print() should be("(-1.0, -2.0, -3.0)")

    v1(1) should be(2.0)

    class MutableVect3(var x: Double, var y: Double, var z: Double) {
      def apply(index: Int): Double = index match {
        case 0 => x
        case 1 => y
        case 2 => z
      }

      def update(index: Int, value: Double): Unit = index match {
        case 0 => x = value
        case 1 => y = value
        case 2 => z = value
      }
    }

    val v3 = new MutableVect3(1, 2, 3)
    v3(0) = 14.0
    v3(0) should be(14.0)
  }

  video("Object declarations", "Ljf6VnunEPU")
  codetask(""" Exercise: (Object implementation)
    Write an object (named TestObject) which increments a member variable by 1. The starting value of the 
    member variable should be 0.""") {
    //solve
    object TestObject {
      var i = 0
      def increment {
        i += 1
      }
    }
    //endsolve

    //test
    TestObject.i should be(0)
    TestObject.increment
    TestObject.i should be(1)
    //endtest
  }

  video("Scala object Declarations: Applications ", "0sE8cH4NRsA")

  video("Scala object Declarations: Companion Objects", "X4aiuETZsZs")
  codetask(""" Exercise: (Companion Object)
    The provided class has an error. The Error is produced by returning a Vect3(...) instance without the keyword
    new. 
    Try to fix this error by using a companion object with the necessary method.
    Tip: Array(1, 2, 3) == Array.apply(1,2,3), both result in a new Array with the elements 1,2,3.""") {
    class Vect3(val x: Double, val y: Double, val z: Double) {
      def +(v: Vect3) = Vect3(x + v.x, y + v.y, z + v.z)
      def -(v: Vect3) = Vect3(x - v.x, y - v.y, z - v.z)
      def unary_-() = Vect3(-x, -y, -z)
      def apply(index: Int): Double = index match {
        case 0 => x
        case 1 => y
        case 2 => z
      }

      def print(): String = "(" + x + ", " + y + ", " + z + ")"
    }

    object Vect3 {
      //solve
      def apply(x: Double, y: Double, z: Double) = new Vect3(x, y, z)
      //endsolve
    }
    //test
    val v3 = Vect3(1.0,2.0,3.0)
    //endtest
    
  }
}