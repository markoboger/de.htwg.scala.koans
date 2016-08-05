package art.of.programming

import org.codetask.koanlib.CodeTaskSuite
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.Scanner

class Ch09_TextFiles extends CodeTaskSuite("Textfiles - IO", 8) {

  import java.io.PrintWriter

  val pw1 = new PrintWriter("numbers.txt")
  pw1.println("5\n4")
  pw1.close
  
  val pw2 = new PrintWriter("numbersAndText.txt")
  pw2.println("5\n4 test\n ")
  pw2.close

  val pw3 = new PrintWriter("fiveNums.txt")
  pw3.println("5\n\n4\n\n3\n2\n1")
  pw3.close

  video("Files and I_O Redirection", "XOb0EKxL6j4")
  video("Reading Files with scala.io.Source", "OG8ZxUGFOTU")
  koan(""" Reading files from the os with Source:
    Like any other language Scala also handles file operations on the operating system. The syntax is exactly the
    same as in Java.
    
    Note: Don't forget to close the file descriptor after handling the operation, otherwise there won't be any
    file descriptors left to handle file operations.
    
    
    The content of the file in this example looks like this (two lines with integers):
    5
    4
    Try to write down the types of the first 4 statments and afterwards the values of the remaining
    statements.""") {
    import io.Source
    val source = Source.fromFile("numbers.txt")
    source.isInstanceOf[scala.io.BufferedSource] should be(true)
    val lines = source.getLines
    lines.isInstanceOf[Iterator[String]] should be(true)

    lines.next.toInt should be(5)
    lines.hasNext should be(true)
    lines.next.toInt should be(4)
    lines.hasNext should be(false)

    source.close
  }

  codetask(""" Exercise: (Reading a file)
    Write the statements to read a file with integers line by line and add them up in the end. The file named "fiveNums.txt" contains empty lines. Try to use 
    higher order methods to get the result.
  
    The content of the file in this example looks like this (7 lines with or withouth integers)
    5
    
    4
  
    3
    2
    1
    """) {
    import scala.io.Source
    var sum = 0
    //solve 
    val source = Source.fromFile("fiveNums.txt")
    val lines = source.getLines
    sum = lines.filter(_.nonEmpty).map(_.toInt).foldLeft(0)(_ + _)
    source.close
    //endsolve

    //test
    sum should be(15)
    //endtest
  }

  video("Files and Exceptions", "lRZmHllsEEo")
  video("Web Sources", "-cIlryPXKk0")
  video("Java Scanner", "d4ykGQSc--4")
  koan(""" Java Scanner:
    Due to the fact that Scala runs on the Java Virtual Machine you could use every java object or class. This feature
    comes in handy when reading from files. The method we covered earlier by using scala.io.Source is good for reading anything
    from a file. If we want to read a set of specific types we could use the java.io.Scanner class, which provides us with
    methods for each datatype primitive as we could see in the example. 
    
    The content of the file in this example looks like this (2 lines):
    5
    4 test    
    """) {
    import java.io.File
    import java.util.Scanner

    val scanner = new Scanner(new File("numbersAndText.txt"))
    scanner.nextInt should be(5)
    scanner.hasNextInt should be(true)
    val (intElement, stringElement) = (scanner.nextInt, scanner.next)
    (intElement, stringElement) should be(4, "test")
    scanner.close
  }

  video(" Writing Files", "qigG0IzjaFU")
  koan(""" Writing to files:
    In some cases you have to write to files on your local system, e.g. Databases, Texteditors and so on. In this case 
    Scala depends on java classes (FileWriter, PrintWriter,...) to archive this goals.""") {
    {
      import java.io.PrintWriter

      val pw = new PrintWriter("file.txt")
      pw.println("first Line")
      pw.println("second Line")
      for (i <- Array(1, 2, 3, 4)) pw.println(i)
      pw.close
    }

    {
      import java.io.FileWriter
      import java.io.PrintWriter
      val pw = new PrintWriter(new FileWriter("file.txt", true))
      pw.println("More lines")
      pw.close
    }
  }

  video("Formatted Output", "tfBp2mdE1QA")
  koan(""" Formatted Output:
    When printing Strings in Scala you can format them to your liking by using a method called printf or 
    string interpolation as we have seen in some previous chapters.""") {
    val num = 42
    f"This number is an Int: $num" should be("This number is an Int: 42")
    val float = 4.2
    f"This number is a float: $float%2.2f" should be("This number is a float: 4,20")
    f"This number is delimited: $float%2f" should be("This number is delimited: 4,200000")
    f"This number is delimited after the period: $float%5.2f" should be("This number is delimited after the period:  4,20")
    f"This number is displayed in scientific notation: $float%5.2e" should be("This number is displayed in scientific notation: 4,20e+00")
    f"This number is displayed in either floating or scientific notation depending on the parameter: $float%g" should be("This number is displayed in either floating or scientific notation depending on the parameter: 4,20000")
    f"This number is converted to hex: $num%x" should be("This number is converted to hex: 2a")
    "The hex representation of the number 15 is %x".format(15) should be("The hex representation of the number 15 is f")
  }
}