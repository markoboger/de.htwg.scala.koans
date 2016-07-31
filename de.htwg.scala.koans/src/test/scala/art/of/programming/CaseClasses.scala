package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class CaseClasses extends CodeTaskSuite("Case Classes", 9) {
  koan(""" Case classes (Problemset): 
    The addition of case classes give the ability to enrich tuples with logic and meaning. We already
    mentioned case classes in some chapters but didn't took a closer look.
    
    In the example below you see what could happen when you don't use case classes. 
        
    Aliasing tuples does not prevent missusage by the user. The user could incorrectly
    use an alias for the wrong function. From the compiler view perspective each alias is just a 
    three tuple. The alias is just a nice way to make the code more readable.""") {
    type Point = (Double, Double, Double)
    type Color = (Double, Double, Double)

    def distance(p1: Point, p2: Point): Double = {
      val dx = p1._1 - p2._1
      val dy = p1._2 - p2._2
      val dz = p1._3 - p2._3
      math.sqrt(dx * dx + dy * dy + dz * dz)
    }

    val p1: Point = (1.0, 1.0, 4.0)
    val p2: Point = (2.0, 3.0, 2.0)
    val distanceBetweenPoints = distance(p1, p2)

    val c1: Color = (2.0, 3.0, 2.0)
    val nonsenseDistanceBetweenColorAndPoints = distance(p1, c1)

    distanceBetweenPoints == nonsenseDistanceBetweenColorAndPoints should be(true)
  }

  koan(""" Case classes (Solution):
    To prevent the clumpsy way of handling tuples without logic Scala introduces the concept
    of case classes with the corresponding keyword.
    
    In the example below the function distance is now limited to Point3D. If you would try to
    invoke this function with a case class called Color this would cause in a compile error.""") {
    case class Point3D(x: Double, y: Double, z: Double)

    def distance(p1: Point3D, p2: Point3D): Double = {
      val dx = p1.x - p2.x
      val dy = p1.y - p2.y
      val dz = p1.z - p2.z
      math.sqrt(dx * dx + dy * dy + dz * dz)
    }

    val p1 = Point3D(1.0, 1.0, 4.0)
    val p2 = Point3D(2.0, 3.0, 2.0)

    distance(p1, p2) should be(3)
  }

  koan(""" Named and default arguments:
    This feature is like many others in Scala purely for readability. Named and default arguments
    add a nice way of calling functions/methods in a way that you don't need to remember which parameter needs to
    be at the first position and so on. You can easily name them accordingly to the methods arguments. You can
    even resort them to you own liking. Another function they add are default arguments. Default arguments set a
    default value to a specific argument that we could miss when invoking a function/method. We could see this example 
    in the last two statements.
    
    Note: You may argue that you still have to take a look into the documentation to find out what the names
    are, yes thats right but if you review your code it's viewer friendlier then without them.""") {
    def evalQuad(a: Int, b: Int, c: Int, x: Int): Int = a * x * x + b * x + c
    evalQuad(2, 4, 5, 2) should be(21)
    evalQuad(a = 2, x = 4, b = 5, c = 2) should be(54)
    evalQuad(2, 4, x = 5, c = 2) should be(72)

    def evalQuadWithDefaults(a: Int = 0, b: Int = 0, c: Int, x: Int): Int = a * x * x + b * x + c
    evalQuadWithDefaults(c = 3, x = 2) should be(3)
  }

  koan(""" Copy method:
    Since case classes are immutable you cannot reassign values to a case class (you can make
    case classes mutable with the keyword var infront of every argument. This style is not recommended!
    For this case classes provide a copy method which create a new instance of the 
    case class. 
    
    Note: There is a way of creating a new case class instance by manually setting the 
    values from an existing instance (s. statement #3). This process is a bad practice and shouldn't be used.
    Instead you could apply the statement #6. This will change only the value x on the fly.""") {
    case class Point3D(x: Double, y: Double, z: Double)

    val p = Point3D(1.0, 2.0, 3.0)
    val q = Point3D(1 + p.x, p.y, p.z)

    val a = p.copy()
    a == p should be(true)

    val b = p.copy(x = p.x + 1)
    b == q should be(true)
  }

  koan(""" Case class pattern matching:
    The most useful case when using case classes is pattern matching. You can apply the known techniques
    from the pattern matching with tuples (using wildcards, using named elements for later use and so on).""") {
    case class Point3D(x: Double, y: Double, z: Double)
    val points = Array.fill(2)(Point3D(math.random, math.random, math.random))
    for (Point3D(x, y, z) <- points) yield math.sqrt(x * x + y * y + z * z)

    case class Student(name: String, age: Int)
    val students = List(Student("Tim", 22), Student("Hans", 22), Student("Jens", 28))
    val ageMultiplied = for (Student(_, age) <- students) yield age * 5
    ageMultiplied should be(List(110, 110, 140))
    val nameConcatenated = for (Student(n, 22) <- students) yield "Student:" + n
    nameConcatenated should be(List("Student:Tim", "Student:Hans"))

    case class StudentWithSchoolclasses(name: String, age: Int, classes: List[String] {})
    val studentsWithClasses = List(StudentWithSchoolclasses("Tim", 22, List("sport", "english")), StudentWithSchoolclasses("Hans", 22, List("biology", "german")), StudentWithSchoolclasses("Jens", 28, List("german")))
    val studentsWithOneClass = for (StudentWithSchoolclasses(n, _, List(a)) <- studentsWithClasses) yield (n, List(a))
    studentsWithOneClass should be(List("Jens", List("german")))
  }

  koan(""" Zipped Type:
    The zipped type is another performance enhancement feature of Scala in case of instance creation.
    The example below shows this performance boost quite clearly. From the chapter about
    collection methods we noticed a method zip which takes two collections and create a single one
    of them. Similiar to the view method several objects/tuples are getting instantiated and afterwards
    thrown away (see statement #3 and #4). 
    By using the zipped method (s. statement #5) Scala creates a instance of the class
    scala.runtime.Tuple2Zipped on which you can invoke methods like foreach, map, filter and so on. Invoking in this
    style the instantiation of several objects/tuples must not be made.""") {
    val a = List(1, 2, 3)
    val b = List("a", "b", "c")

    a.zip(b) should be(List((1, a), (2, b), (3, "c")))
    a.zip(b).map(t => t._2 * t._1)

    val response = (a, b).zipped.map((integer, string) => string * integer)
    response should be(List("a", "bb", "ccc"))
  }
}