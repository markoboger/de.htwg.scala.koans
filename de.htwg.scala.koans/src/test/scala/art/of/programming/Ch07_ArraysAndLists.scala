package art.of.programming

import org.codetask.koanlib.CodeTaskSuite

class Ch07_ArraysAndLists extends CodeTaskSuite("Arrays and Lists", 5) {
  
  video("Array Basics", "J8WWeQMD5jg")
  video("List Basics", "RYyaskHb8C8")
  koan(""" Basics of Array and List:
    Basic collections like arrays and lists are available from the get-go. Known from Java the collections
    have distinct behaviour. In Scala these two collections have a way bigger distinction. Due to functional programming
    you strive for immutability. In Scala Arrays are implemented mutable and Lists are immutable.""") {
    val a = Array(1, 2, 3, 4, 5)
    a(2) should be(3)

    a.length should be(5)
    a.size should be(5)

    a(2) = 42
    a(2) should be(42)

    val l = List(1, 2, 3, 4, 5)
    42 :: l should be(List(42, 1, 2, 3, 4, 5))
    l should be(List(1, 2, 3, 4, 5))

    1 :: 2 :: 3 :: Nil should be(List(1, 2, 3))
  }

  video("Recursive fill function for Arrays", "PrVj8CBgK5o")
  codetask("""Exercise: (Recursive Array filling)
    Write a function which fills an Array with a given number recursivly with the knowledge you gathered from this chapter.
    """) {
    def fillArray(array: Array[Int], value: Int, i: Int): Unit = {
      //solve
      if (i < array.length) {
        array(i) = value
        fillArray(array, value, i + 1)
      }
      //endsolve
    }

    //test
    val array = Array(1, 1, 1, 1)
    fillArray(array, 0, 0)
   
    array should be(Array(0, 0, 0, 0))
    //endtest
  }

  video("Applying higher order functions", "5PlT3AumYgg")
  codetask(""" Exercise: (Operate on array with higher order methods)
    Write a function which adds up all elements recursivly with the use of a higher function (lambda literal).
    
    Note: Make use of the Functions chapter section Higher order methods.""") {
    def operateOnArray(arr: Array[Int], i: Int, f: (Int, Int) => Int): Int = {
      //solve
      if (i < arr.length - 1) {
        f(arr(i), operateOnArray(arr, i + 1, f))
      } else {
        arr(i)
      }
      //endsolve
    }

    val arr = Array(1, 2, 3, 4)
    //test
    operateOnArray(arr, 0, _ + _) should be(10)
    //endtest
  }

  codetask(""" Exercise: (Appending elements to a list)
  Write a function which appends the first n natural numbers recursively to a list in reverse order. 
  """) {
    def append(n: Int, list:List[Int]): List[Int] = {
      //solve
      if (n < 1) Nil
      else n :: append(n - 1,list)
      //endsolve
    }
    //test
      append(5,Nil) should be(5::4::3::2::1::Nil)
    //endtest
  }

  koan(""" List Functions:
    Each collection has it's special methods which can be invoked. 
    You can read about those methods in the scala documentation.""") {
    val lst = List(1, 2, 3, 4, 5)
    lst.head should be(1)
    lst.tail should be(List(2, 3, 4, 5))
    lst.last should be(5)
  }

  video("List functions", "v_AofFV8tCU")
  codetask(""" Exercise: (Recursive summation of elements)
   Write a function which adds up all elements recursivly with the use of a higher function (lambda literal).
    
   Note: Make use of the Functions chapter section Higher Functions.""") {
    def operateOnList(lst: List[Int], f: (Int, Int) => Int): Int = {
      //solve
      if (lst == Nil) 0
      else f(lst.head, operateOnList(lst.tail, f))
      //endsolve
    }

    val lst = List(1, 2, 3, 4)
    //test
    operateOnList(lst, _ + _) should be(10)
    //endtest
  }

  video("Scala library fill method", "El1vc04cUto")
  video("Scala library tabulate method", "wNn-TvgH6M8")
  koan(""" Fill/Tabulate Method:
    For quickly setting up an Array or List Scala provides a method for this for each collection""") {
    Array.fill(10)(math.random).size should be(10)
    Array.fill(5)(0) should be(Array[Int](0, 0, 0, 0, 0))
    Array.tabulate(5)(_ + 1) should be(Array[Int](1, 2, 3, 4, 5))

    List.fill(10)(math.random).size should be(10)
    List.fill(5)(0) should be(Array[Int](0, 0, 0, 0, 0))
    List.tabulate(5)(_ => math.random).size should be(5)
  }

  codetask(""" Exercise: (Custom fill function)
    Write a custom implementation of a fill method for Lists.
    Make use of recursion""") {
    def fillList(n: Int, value: Double): List[Double] = {
      //solve
      if (n < 1) Nil
      else value :: fillList(n - 1, value)
      //endsolve
    }

    val lst = fillList(3, 2.0)
    //test
    lst should be(List(2.0, 2.0, 2.0))
    //endtest
  }

  video("Collection methods - Data Access", "iNTFdp0uhHM")
  koan(""" Collection Methods: Data Access
    Arrays and Lists offer plenty of methods to access the data in itself.
    
    The examples below show these methods on the array collection. It is identical with
    the list collection.
    
    These methods don't change the actual data in the collection.""") {
    val a = Array(5, 2, 0, 1, 2, 3)

    a.drop(2) should be(Array(0, 1, 2, 3))
    a.init should be(Array(5, 2, 0, 1, 2))
    a.slice(2, 4) should be(Array(0, 1))
//   a.splitAt(3) should be((Array(5, 2, 0), Array(1, 2, 3)))
    a.take(3) should be(Array(5, 2, 0))
    a.dropRight(3) should be(Array(5, 2, 0))
    a.takeRight(3) should be(Array(1, 2, 3))
  }

  video("Collection methods - Information Gain ", "_I7L2A5F_Iw")
  koan(""" Collection Methods: Information Gain 
    The examples below show these methods on the array collection. It is identical with
    the list collection.
    These methods don't change the actual data in the collection.""") {
    val a = Array(5, 2, 0, 1, 2, 3)

    a.contains(3) should be(true)
    a.endsWith(Array(1, 2, 3)) should be(true)
    a.endsWith(List(1, 2, 3)) should be(true)
    a.startsWith(Array(9, 8, 2)) should be(false)
    a.isEmpty should be(false)
    a.nonEmpty should be(true)

    a.indexOf(5) should be(0)
    a.indexOf(44) should be(-1)
    a.indexOf(1, 3) should be(3)
  }

  video("Collection Methods - Miscellaneous", "7LN9hyUbcJ8")
  koan(""" Collection Methods: Miscellaneous
    The examples below show these methods on the array collection. It is identical with
    the list collection.
    These methods don't change the actual data in the collection.""") {
    val a = Array(5, 2, 0, 1, 2, 3)

    a.diff(Array(0, 1, 2)) should be(Array(5, 2, 3))
    a.diff(Array(44, 12, 5)) should be(Array(2, 0, 1, 2, 3))
    a.distinct should be(Array(5, 2, 0, 1, 3))

    a.mkString should be("520123")
    a.mkString(",") should be("5,2,0,1,2,3")
    a.mkString("(", ",", ")") should be("(5,2,0,1,2,3)")

    a.patch(2, Nil, 2) should be(Array(5, 2, 2, 3))
    a.patch(2, Array(42, 21), 1) should be(Array(5, 2, 42, 21, 1, 2, 3))

    a.reverse should be(Array(3, 2, 1, 0, 2, 5))

    a.toList should be(List(5, 2, 0, 1, 2, 3))

    a.min should be(0)

    val arr = Array(1, 2, 3)
    val lst = List("Hi", "Bye")

    arr.zip(lst) should be(Array((1, "Hi"), (2, "Bye")))
    lst.zip(arr) should be(List(("Hi", 1), ("Bye", 2)))

    arr.zipWithIndex should be(Array((1, 0), (2, 1), (3, 2)))
    lst.zipWithIndex should be(Array(("Hi", 0), ("Bye", 1)))
  }

  koan(""" Eval Problem:
    In Scala you have to be aware when passing values to functions.
    Try to guess the outcome of the function calls.""") {
    var i = 0.0

    def fillList(n: Int, value: Double): List[Double] = {
      if (n < 1) Nil
      else value :: fillList(n - 1, value)
    }

    List.fill(5)({ i += 1.0; i }) should be(List(1.0, 2.0, 3.0, 4.0, 5.0))
    i = 0.0
    fillList(5, { i += 1.0; i }) should be(List(1.0, 1.0, 1.0, 1.0, 1.0))
  }

  video("Higher order methods - map and filter", "fkepwLhK86I")
  video("Higher order methods in general", "6ChKZeffV_4")
  koan(""" Higher Functions:
    By default Scala offers a set of higher functions like map, filter, reduce, partition and so on.
    With these methods you can achieve a similar behaviour as with loops. In a later chapter these methods are 
    used in the for comprehension.""") {
    val a = Array(5, 2, 9, 3)
    a.map(_ * 2) should be(Array(10, 4, 18, 6))
    a.map(i => i * 2) should be(Array(10, 4, 18, 6))

    a.filter(i => i % 2 == 0) should be(Array(2))
    a.filter(i => i % 2 == 1).map(_ * 2) should be(Array(10, 18, 6))

    a.count(_ % 2 == 1) should be(3)
    a.dropWhile(_ < 9) should be(Array(9, 3))
    a.exists(_ % 9 == 0) should be(true)

    val b = Array(1, 2, 3)
    b.map(i => Array.fill(i)(i)) should be(Array(Array(1), Array(2, 2), Array(3, 3, 3)))
    b.flatMap(i => Array.fill(i)(i)) should be(Array(1, 2, 2, 3, 3, 3))

    b.forall(_ < 10) should be(true)
    b.indexWhere(_ % 2 == 0) should be(1)
    b.lastIndexWhere(_ % 2 == 1) should be(2)

//   b.partition(_ % 2 == 0) should be((Array(2), Array(1, 3)))
    val (even, odd) = b.partition(_ % 2 == 0)
    odd should be(Array(1, 3))

    b.takeWhile(_ < 3) should be(Array(1, 2))
    b.dropWhile(_ < 3) should be(Array(3))
  }

  video("Reduce and fold", "bnOTEfNEQzw")
  koan(""" Reduce and fold:
    Those methods come in handy when you are trying to applying a certain function or operation on elements in a dataset.
    
    These methods are available for all collections in scala""") {
    val a = Array(5, 2, 9, 3)

    a.reduceLeft(_ + _) should be(19)
    a.reduceLeft((a, b) => a + b) should be(19)

    val findMax = (x: Int, y: Int) => {
      x max y
    }
    a.reduceLeft(findMax) should be(9)

    def min(x: Int, y: Int) = {
      x min y
    }
    a.reduceLeft(min) should be(2)

    a.reduceRight(_ + _) should be(19)
    a.reduceRight(_ - _) should be(9)

    a.foldLeft(0)(_ + _) should be(19)
    a.foldLeft("")(_ + _) should be("5293")
  }

  video("Combinatorial iterator methods", "-SsecWc0ohI")
  koan(""" Combinatorial functions:
    The possibility to do combinatorial, permutations and sliding within Scala you just call the responsible function. 
    Each function delivers an Iterator. Through simple iteration functions like next() and hasNext() you can iterate
    through the Iterator. Keep in mind that it will throw an exception if you call next after the last item.
    
    Note: In this example each statement creates an Array due to testing capabilities. The problem is that each function
    returns an iterator and testing this was undoable. This is why you should enter the right multi dimensional array instead
    of writing the Iterator.""") {
    val a = Array(1, 2, 3)
    a.combinations(2).toArray should be(Array(Array(1, 2), Array(1, 3), Array(2, 3)))
//    a.grouped(3).toArray should be(Array(Array(1, 2), Array(3)))
    a.inits.toArray should be(Array(Array(1, 2, 3), Array(1, 2), Array(1), Array()))
    a.tails.toArray should be(Array(Array(1, 2, 3), Array(2, 3), Array(3), Array()))
    a.permutations.toArray should be(Array(Array(1, 2, 3), Array(1, 3, 2), Array(2, 1, 3), Array(2, 3, 1), Array(3, 1, 2), Array(3, 2, 1)))
//    a.sliding(3).toArray should be(Array(Array(1, 2), Array(2, 3)))
  }

  video("Strings as Collections", "9KyvF-8UmSw")
  koan(""" Collection String:
    Strings are a special collection within Scala. Every function which were applied to lists or arrays could be
    invoked on a string. E.g. indexing, map, count and so on.""") {
    val s = "Hallo"
    s(1) should be('a')

    s.map(c => (c + 1).toChar) should be("Ibmmp")
    s.count(c => "aeiou".contains(c)) should be(2)

    val str = "Hello World"
    str.split(" ") should be(Array("Hello", "World"))

    val strSpaces = "Hello      World"
    strSpaces.split(" +") should be(Array("Hello", "World"))

    "1 2 3 4 5 6".split(" ").map(_.toInt) should be(Array(1, 2, 3, 4, 5, 6))
  }
}