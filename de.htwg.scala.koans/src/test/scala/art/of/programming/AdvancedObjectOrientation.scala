package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class AdvancedObjectOrientation extends CodeTaskSuite("Advanced Object Orientation", 11) {
  codetask(""" Exercise: (Inheritance and Polymorphism)
    Implement two classes Rectangle and Circle which inherit from the abstract shape class:
    
    Rectangle:
        values:
          width : Double
          height: Double
          color : Color
       
    Cirle:
        values:
          radius: Double
          color : Color          

        
    methods:
          override the methods from Shape (code below). Add an additional print statement
          when calling the method draw method depending on the element ("draw <elementname>").""") {
    import java.awt.Color
    import java.awt.Graphics2D
    import java.awt.geom.Rectangle2D

    abstract class Shape(private val color: Color) {
      def area: Double
      def perimeter: Double
      def draw(g: Graphics2D) {
        g.setPaint(color)
      }
    }

    object Shape {
      def main(args: Array[String]) {
        val r = new Rectangle(10, 4, Color.red)
        val c = new Circle(5, Color.green)
        printShape(r)
        printShape(c)
      }

      def printShape(s: Shape) {
        println("Area = " + s.area)
        println("Perimeter = " + s.perimeter)
      }

    }

    // solve
    class Rectangle(val width: Double, val heigth: Double, val color: Color) extends Shape(color) {
      override def area: Double = width * heigth
      override def perimeter: Double = 2 * (width + heigth)
      override def draw(g: Graphics2D) {
        super.draw(g)
        println("draw rectangle")
      }
    }
    
    class Circle(val radius: Double, val color: Color) extends Shape(color) {
      override def area: Double = math.Pi * radius * radius
      override def perimeter: Double = 2 * math.Pi * radius
      override def draw(g: Graphics2D) {
        super.draw(g)
        println("draw circle")
      }
    }
    // endsolve
  }

  codetask(""" Exercise: (Force Immutability)
    When implementing classes a good way to harden the code base against missuse you can force immutability.
    By this enforcement classes which are subclasses of this class can't override and then missuse the method or even
    the whole class against the initial functional idea.
    Firstly try to write an immutable class named Immutable without a body and argumentlist
    Secondly try to write an immutable method named immutable with an empty body and argumentlist.""") {
    // solve
    final class Immutable
    
    class ImmutableMethod() {
      final def immutable() {}
    }
    // endsolve

  }

  codetask(""" Exercise: (Traits example)
    Implement two classes Rectangle and Circle which inherit from the abstract shape class:
    
    (abstract class)
    1. Animal:
        speak
    
    (trait)
    2. WaggingTail:
        whipTail
      
    3. FourLeggedAnimal:
        walk
        run
      
    (class)
    4. Dog which extends the Animal class with WaggingTail and FourleggedAnimal traits
      implement the methods:
        speak => print woof
        whipTail => print tail whipping
        walk => print walking
        run => print running""") {
    // solve
    abstract class Animal {
      def speak
    }
   
    trait WaggingTail {
      def whipTail
    }

   trait FourLeggedAnimal {
      def walk
      def run
    }

   class Dog extends Animal with WaggingTail with FourLeggedAnimal {
      def speak = println("woof")
      def whipTail = println("tail whipping")
      def walk = println("walking")
      def run = println("running")
    }
    // endsolve
  }

  codetask("""Exercise: (Trait addition during object instantiation)
    Scala allows to add a trait to an object instance when creating it.
    Try to write:
    
    1. An empty class "DavidBanner" 
    2. A trait "Angry" that just prints "You won't like me..."
    3. An object Hulk with a variable (val) that instantiates an Object from The class
      with the additional trait.""") {
    // solve
    class DavidBanner
 
    trait Angry {
      println("You won't like me ...")
    }
 
    object Hulk {
      val hulk = new DavidBanner with Angry
    }
    // endsolve
  }

  codetask(""" Exercise: (Abstract and concrete fields in traits)
    Write a trait "Trait" which has two fields:
      abstractField of type Int
      concreteField with a value of 10""") {
    // solve
    trait Trait {
      var abstractField: Int
      val concreteField = 10
    }
    // endsolve
  }

  codetask(""" Excercise: (Using a trait like a Java abstract class)
    You can use traits like abstract classes in Java. Implementation is provided 
    for the speak method in the Pet trait, so implementing classes don’t have to override it. 
    
    Implement following classes:
    1. class Dog
    2. class Cat""") {
    trait Pet {
      def speak { println("Yo") }
      def comeToMaster
    }

    // solve
    class Dog extends Pet {
      def comeToMaster = println("I'm coming!")
    }

    class Cat extends Pet {
      override def speak = println("meow")
      def comeToMaster = println("That's not gonna happen.")
    }
    // endsolve

    object Test {
      val zeus = new Dog
      val cleopatra = new Cat
      // test
      zeus.comeToMaster == "I'm coming!" should be(true)
     
      cleopatra.comeToMaster == "That's not gonna happen." should be(true)
      cleopatra.speak == "meow" should be(true)
      // endtest
    }
  }

  codetask(""" Exercise: (Traits as simple mixins)
    Implement the following:
    
    1. A trait "Tail" with wagTail
    2. An abstract class "Pet" with the abstract speak and ownerIsHome 
    3. An explicit class "Dog"""") {
    // solve
    trait Tail {
      def wagTail = println("tail is wagging")
    }
  
    abstract class Pet {
      def speak
      def ownerIsHome = println("excited")
    }

    class Dog extends Pet with Tail {
      def speak { println("woof") }
    }
    // endsolve

    object Test {
      val zeus = new Dog
      // test
      zeus.ownerIsHome == "excited" should be(true)
      zeus.wagTail == "tail is wagging" should be(true)
      zeus.speak == "woof" should be(true)
      // endtest
    }
  }

  codetask(""" Exercise: (Parametric Traits)
    If you are familiar with the Java interfaces you can treat this equally (Scala Traits are more powerful but we will discuss 
    this in a later chapter)
    
    Try to write a parametric trait with following methods:
      push
      pop
      isEmpty
      peek
    Choose "A" as your parametric type and in case where you need "elem" for an element.
    
    Tip: If you unsure how to write this, take a look in a previous chapter. We discussed this topic with functions/methods""") {
    // solve
    trait Stack[A] {
      def push(elem: A)
      def pop(): A
      def isEmpty: Boolean
      def peek: A
    }
    // endsolve
  }

  codetask(""" Exercise: (Advanced Parametric Functions/Methods) 
    In Scala you not only have the ability to include a parametric type. You can also tell the level of inheritance
    the functions/methods/classes should take in account.
    By adding the special symbols you can decide which level of inheritance you want to include:
      A <: Seq[A] , this will let you call the method with types who are subtypes of Seq
      A <% Seq[A] , this will let you get the same result as <: but with the addition of types which are implicitly convertable
      to Seq
      
    Write a parametric method "bubbleSort" which sorts an array with the bubble sort algorithm (descending). The method should be able to handle
    Int, Double and so on.
    
    Note: This implementation does not show ability to change the order in which the elements should be sorted (see next example)""") {
    // solve
    def bubbleSort[A <% Ordered[A]](a: Array[A]) 
    {
      for (i <- 0 until a.length - 1) {
        for (j <- 0 until a.length - 1 - i) {
          if (a(j + 1) < a(j)) {
            val tmp = a(j)
            a(j) = a(j + 1)
            a(j + 1) = tmp
          }
        }
      }
    }
    // endsolve
  }

  codetask(""" Exercise: (Advanced Parametric Functions/Methods)
    As we have seen in the previous codetask the method bubbleSort had no chance of altering the sorting order.
    
    Try to fix this problem by using lambda literals and multiple argument lists. Use "func" as your the reference for the lambda literal.""") {
    
    def bubbleSort[A](a: Array[A])(func: (A, A) => Boolean) 
    {
      // solve
      for (i <- 0 until a.length - 1) {
        for (j <- 0 until a.length - 1 - i) {
         
          if (func(a(j + 1), a(j))) 
          {
            val tmp = a(j)
            a(j) = a(j + 1)
            a(j + 1) = tmp
          }
        }
      }
      // endsolve
    }
    val nums = Array(15, 1, 9, 4, 99)
    bubbleSort(nums)(_ > _)
    // test
    nums should be(Array(99, 15, 9, 4, 1))
    bubbleSort(nums)(_ < _)
    nums should be(Array(1, 4, 9, 15, 99))
    // endtest
  }

  codetask(""" Exercise: (Loan Pattern)
    The loan pattern is an ability to make the code more readable. In a scenario where you have lots of try/catch blocks
    you'll lose sight of your code due to a fast majority of boiler plate code. Do reduce this a bit Scala supports the 
    reduction with Lambda literals. 
    
    Note: Try to reduce the try/catch boiler plate code by outsourcing the open and close statements of a FileInputStream
    to a method.""") {
    import java.io.FileInputStream
    import java.io.File
    import java.io.FileNotFoundException
    import java.io.IOException

    object FileOpenCloser {
      def openAndClose[A](fileName: String)(body: FileInputStream => A): A = {
        val fis = new FileInputStream(fileName)
        try {
          body(fis)
        } finally {
          fis.close()
        }
      }

      def main(args: Array[String]) {
        val file = new File("file.txt")
        try {
          openAndClose(file.getAbsolutePath)(fis => {
            val buf = new Array[Byte](file.length().toInt)
            fis.read()
            println(new String(buf))
          })
        } catch {
          case ex: FileNotFoundException =>
            println("File not found")
            ex.printStackTrace()
          case ex: IOException =>
            println("Error reading the file")
            ex.printStackTrace()
        }
      }
    }
  }
} 