package de.htwg.scala.exercises

import de.htwg.scala.koans.KoanSuite

// meditate on Exercise 01 about Asserts to see how the Scala Koans work
class Ex01_Asserts extends KoanSuite {

  koan("asserts can take a boolean argument") {
    assert(true) // enter true here
  }

  koan("asserts can include a message") {
    assert(true, "This should have the value true")
  }

  koan("true and false values can be compared with should matchers") {
    true should be(true) // be true
  }

  koan("booleans in asserts can test equality") {
    val v1 = 4
    val v2 = 4
    assert(v1 === v2)
  }

  koan("sometimes we expect you to fill in the values") {
    assert(1 + 1 == 2)
  }
}
