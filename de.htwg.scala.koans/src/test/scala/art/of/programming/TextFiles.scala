import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class TextFiles extends CodeTaskSuite("Textfiles - IO", 8) {
  codetask("""Exercise: (Import statements)
    Write down the import statements for Date, NoSuchElementException, readInt and every collection.
    Try to enter the necessary statements with full the package names (just for testing purpose), e.g. scala.math.BigInt instead of math.BigInt."""){
    {
      // solve
      import java.util.{Date, NoSuchElementException}
      import scala.io.StdIn.readInt
      import scala.collection._
      // endsolve
    }
  }

  koan(""" Reading files from the os with Source:
    Like any other language Scala also handles file operations on the operating system. The syntax is exactly the
    same as in Java.
    
    Note: Don't forget to close the file descriptor after handling the operation, otherwise there won't be any
    file descriptors left to handle file operations.
    
    
    The content of the file in this example looks like this (two lines with integers):
    5
    4
    
    
    Try to write down the types of the first 4 statments and afterwards the values of the remaining
    statements."""){
    import io.Source
    val source = Source.fromFile("file.txt")
    source should be("scala.io.BufferedSource")
    val lines = source.getLines
    lines should be("Iterator[String]")

    
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
    """){
    import scala.io.Source  
    // solve 
    val source = Source.fromFile("fiveNums.txt")
    val lines = source.getLines
    val nums = lines.filter(_.nonEmpty).map(_.toInt)  
    source.close
    // endsolve
    
    // test
    nums should be(15)
    // endtest
  }

  codetask(""" Exercise: (Catching Exceptions)
    Write the statements to read from a file but focus on catching the exception (e) if the file is not found.
    Print out the stacktrace when the exception is caught. Use "file.txt" as the filename.
    
    Note: Don't worry about closing the file descriptor this time."""){
    import io.Source
    // solve
    try{
      val source = Source.fromFile("file.txt")
    } catch {
      case e:java.io.FileNotFoundException =>  e.printStackTrace()
    }   
    // endsolve
  }

  koan(""" Java Scanner:
    Due to the fact that Scala runs on the Java Virtual Machine you could use every java object or class. This feature
    comes in handy when reading from files. The method we covered earlier by using scala.io.Source is good for reading anything
    from a file. If we want to read a set of specific types we could use the java.io.Scanner class, which provides us with
    methods for each datatype primitive as we could see in the example. 
    
    The content of the file in this example looks like this (2 lines):
    5
    4 test    
    """){
    import java.io.File
    import java.util.Scanner
    
    val scanner = new Scanner(new File("file.txt"))
    scanner.nextInt should be(5)
    scanner.hasNextInt should be(true)
    val (intElement, stringElement) = (scanner.nextInt, scanner.next)
    (intElement, stringElement) should be(4,"test")
    scanner.close
  }
  
  koan(""" Writing to files:
    In some cases you have to write to files on you local system, e.g. Databases, Texteditors and so on. In this case 
    Scala depends on java classes (FileWriter, PrintWriter,...) to archive this goals."""){
    {
      import java.io.PrintWriter

      val pw = new PrintWriter("file.txt")
      pw.println("first Line")
      pw.println("second Line")
      for(i <- Array(1,2,3,4)) pw.println(i)
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

  koan(""" Formatted Output:
    When printing Strings in Scala you can format them to your liking by using a method called printf or 
    string interpolation as we seen in some previous chapters."""){
      printf("The number is %d",42) should be("The number is 42")
      printf("The number is a float %f",4.2) should be("The number is a float 4.200000")
      printf("The number is delimited %2f",4.2) //4.200000
      printf("The number is delimited after the period %5.2f",4.2) //4.20
      printf("The number will be displayed in scientific notation %5.2e",4.2e10)
      printf("The number will be displayed in either floating or scientific notation depending on the parameter %g", 4.2)
      printf("The number will be converted to hex %x",15)
      "The hex representation of the number 15 is %x".format(15) should be("The hex representation of the number 15 is f")
  }
}