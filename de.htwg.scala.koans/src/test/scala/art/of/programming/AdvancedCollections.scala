package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class AdvancedCollections extends CodeTaskSuite("Advanced Collections", 12) {
  koan(""" Buffer: 
    The idea of a buffer is very similar to arrays. They have a specific order and you can
    get elements by indexing.
        
    The Buffer is a Sequence which is mutable. There are different subclasses of Buffer e.g.
    ListBuffer, ArrayBuffer and so on.    
    
    With buffers you can get rid of var statements by writing val buffers.
    This is because the reference doesn't have to change it will be the mutable object that 
    has to change.
    
    Note: Buffers do only have a mutable implementation!""") {
    import scala.collection.mutable
    import scala.collection.mutable.ArrayBuffer

    val buf = mutable.Buffer(1, 2, 3, 4)

    buf.map(_ * 2)
    buf should be(ArrayBuffer(1, 2, 3, 4))

    buf += 42
    buf should be(ArrayBuffer(1, 2, 3, 4, 42))

    15 +=: buf
    buf should be(ArrayBuffer(15, 1, 2, 3, 4, 42))

    buf(2) = 101
    buf should be(ArrayBuffer(15, 1, 101, 3, 4, 42))

    buf.remove(0)
    buf should be(ArrayBuffer(1, 101, 3, 4, 42))

    buf.insert(1, 4)
    buf should be(ArrayBuffer(1, 4, 101, 3, 4, 42))

    buf.insertAll(1, List(42, 42, 42))
    buf should be(ArrayBuffer(1, 42, 42, 42, 4, 101, 3, 4, 42))
  }

  koan(""" Maps:
    Maps are quite different from other collections. They have parametric polymorphism but they
    are parametric on two different types.  
    Maps associate a key with a value. It takes tuples as elements. Setting up a map is usually 
    constructed with -> notation to create tuples. By default maps are immutable but you can 
    enforce mutability if needed.
    The benefit of using a map is to lookup elements very efficient without iterating over each one. 
    Instead of referencing elements in an array with an index you can use a key to get the 
    right element. 
    
    Maps are unordered. Maps even offer pattern matching which make the code more readable""") {
    "one" -> 1 should be(("one", 1))

    val fullTypeName = Map(("one", 1), ("two", 2), ("three", 3))
    fullTypeName.isInstanceOf[scala.collection.immutable.Map[java.lang.String, Int]] should be(true)

    val m1 = fullTypeName
    val m2 = Map("one" -> 1, "two" -> 2, "three" -> 3)
    m1 == m2 should be(true)

    m2("one") should be(1)
    m2 + ("four" -> 4) should be(Map("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4))
    m2 should be(Map("one" -> 1, "two" -> 2, "three" -> 3))

    case class Student(name: String, grade: Int)
    val students = Seq(Student("Mark", 90), Student("John", 80), Student("Jane", 95))
    students.find(_.name == "John") should be(Some(Student("John", 80)))

    val studentMap = students.map(s => s.name -> s).toMap
    studentMap("Mark") should be(Student("Mark", 90))

    try {
      studentMap("Ted")
    } catch {
      case e: java.util.NoSuchElementException => println("this will crash")
    }

    studentMap.get("Ted") should be(None)
  }

  koan(""" Sets
    Sets are derived from the mathematical term of Set. Which is a collection of
    elements where duplicates are not allowed and order doesn't matter.
    Sets can be immutable and mutable, by default the set is immutable.""") {
    val set = Set(1, 2, 3)
    set.isInstanceOf[scala.collection.immutable.Set[Int]] should be(true)

    set + 1 should be(Set(1, 2, 3))
    set - 1 should be(Set(2, 3))

    set(3) should be(true)
    set(14) should be(false)

    import collection.mutable
    val mutableSet = mutable.Set(1, 2, 3)

    mutableSet(78) should be(false)
    mutableSet += 78
    mutableSet(78) should be(true)
    mutableSet(12) = true
    mutableSet(12) should be(true)

    val mutableMappedSet = mutable.Set(1, 2, 3).map(_ * 2)
    mutableMappedSet should be(Set(2, 4, 6))

    List(1, 1, 1, 1, 2, 42, 84).toSet should be(Set(1, 2, 42, 84))

    val unionSet = Set(1, 2, 3) union Set(3, 4, 5)
    unionSet should be(Set(5, 1, 2, 3, 4))

    val intersectSet = Set(1, 2, 3) intersect Set(2, 3, 4)
    intersectSet should be(Set(2, 3))

    "one" -> 1 should be(("one", 1))

    val fullTypeName = Map(("one", 1), ("two", 2), ("three", 3))
    fullTypeName.isInstanceOf[scala.collection.immutable.Map[java.lang.String, Int]] should be(true)

    val m1 = fullTypeName
    val m2 = Map("one" -> 1, "two" -> 2, "three" -> 3)
    m1 == m2 should be(true)

    m2("one") should be(1)
    m2 + ("four" -> 4) should be(Map("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4))
    m2 should be(Map("one" -> 1, "two" -> 2, "three" -> 3))

    case class Student(name: String, grade: Int)
    val students = Seq(Student("Mark", 90), Student("John", 80), Student("Jane", 95))
    students.find(_.name == "John") should be(Some(Student("John", 80)))

    val studentMap = students.map(s => s.name -> s).toMap
    studentMap("Mark") should be(Student("Mark", 90))

    try {
      studentMap("Ted")
    } catch {
      case e: java.util.NoSuchElementException => println("this will crash")
    }

    studentMap.get("Ted") should be(None)
  }
}