package art.of.programming

import org.scalatest.Matchers
import org.codetask.koanlib.CodeTaskSuite

class Conditionals extends CodeTaskSuite("Conditionals", 2) {
  video("Conditionals", "DYUJGtNcuYk")
  koan(""" If conditional:
    The If conditional is in almost everything the same as in any other programming language. 
    The only fact which differs from some other languages is that the conditional always returns a value.
    The last expression in the code block is returned as result.      
    In some cases this is very benefitial. For example if you don't want to introduce a variable to store
    a result. In certain languages you always have to declare a variable in which you store the value of the
    conditional, in scala you can use a val to store the result of it.""") {
    val number = 12
    val response = if (number >= 0) "Positive Number" else "Negative Number"
    response should be("Positive Number")
  }

  video("Code Blocks", "WUOV9Km0o_8")
  koan(""" Code block:
    Code blocks are expressions seperated from the code with curly braces. Examples for code blocks
    are method bodies, conditionals, loops or static code blocks. The result of a code block is always the
    last statement""") {
    val response = {
      4
      5
      6
    }
    response should be(6)

    val number = -42
    val conditionResponse = if (number >= 0) {
      println("The number is positive")
      "Positive Number"
    } else {
      println("The number is negative")
      "Negative Number"
    }

    conditionResponse should be("Negative Number")
  }

  video("Comparison Operators", "XtDbWYHOQGg")
  koan(""" Comparision Operator:
    In Scala the basic comparison operators are implemented on the primitiv datatypes. 
    To compare strings there are to seperate comparing methods. The first one is to check for equality value wise(see 6th and 7th statement). 
    The other one is to check for identity, checking if two objects are the same (8th and 9th statement).
    
    The last statement is a bit tricky due to scalas compiler and performance enhancements.""") {
    5 < 4 should be(false)
    10 > 2 should be(true)
    0 >= -1 should be(true)
    2 <= 2 should be(true)
    'a' > 'z' should be(false)

    "hi" == "hy" should be(false)
    "hi" != "hy" should be(true)

    "hi" eq "hy" should be(false)
    "hi" eq "hi" should be(true)
  }

  video("Boolean logic", "qNly4zSs9-4")
  koan(""" Boolean Logic:
    The boolean logic is equal to the java language and offers the default behaviour.""") {
    5 > 2 && 'a' != 'b' should be(true)

    val x = true
    !x || "Hello".length >= 4 should be(true)
  }

  video("Operator Precedence", "hF-Kf_zG_G0")
  koan(""" Operator Precedence:
    To know when which part of a calculation or statement is processed first, Scala has 
    a fix precedence which is documentated and listed below:
      
    (all letters)
    |
    ^
    &
    < >
    = !
    :
    + -
    * / %
    (all other special characters)
    
    Solve the result of the statements""") {
    3 + 4 * 5 should be(23)
    (3 + 4) * 5 should be(35)

    3 + 3 < 4 * 2 should be(true)
  }

  video("Nested Ifs", "ZT3zAIQhDE8")
  koan(""" Nested If's:
    If conditionals could be nested as seen below. A certain level of nested is okay but 
    in case of code quality, maintainability and readability it isn't a good practice if 
    the level of nested if's is above two""") {
    val categorie = "Food"
    val item = "Lasagne"
    val price = if (categorie == "Beverage") {
      if (item == "Coke") 2.6
      else if (item == "Water") 1.2
      else 0.0
    } else if (categorie == "Food") {
      if (item == "Pizza") 6.9
      else if (item == "Risotto") 7.0
      else 7.5
    } else 0.0
    price should be(7.5)
  }

  video("Bitwise Arithmetic", "8CpZLfe0Lug")
  koan(""" Bitwise Arithmetic:
    For low level programming and encryption bitwise arithmetic is mandatory.
    It contains basic operations as &,|,^,~ and bitwise shifting""") {
    42 & 6 should be(2)
    13 | 4 should be(13)
    5 ^ 4 should be(1)
    ~7 should be(-8)
    1 << 1 should be(2)
    4 << 3 should be(32)
    10 >> 1 should be(5)
  }
}
