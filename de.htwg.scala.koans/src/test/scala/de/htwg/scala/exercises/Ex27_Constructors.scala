package de.htwg.scala.exercises

import de.htwg.scala.koans.KoanSuite

class Ex27_Constructors extends KoanSuite {

  class AboutConstructorWithAuxiliaryConstructor(val name: String) {
    // invoke auxiliary constructor
    def this() {
      // what happens if you comment out the following line?
      this ("defaultname") // if commented, compilation problem: 'this' expected but '}' found
    }
  }

  koan("Primary constructor specified with a parameter requires that parameter to be passed in") {
    val aboutMe = new AboutConstructorWithAuxiliaryConstructor()
    aboutMe.name should be ("defaultname")
  }

  class AboutClassWithNoClassParameter

  koan("Class with no class parameters is called with no arguments") {
    // add parameter to make this fail
    val aboutMe = new AboutClassWithNoClassParameter

  }
}
