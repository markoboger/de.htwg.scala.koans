package de.htwg.scala.exercises

import de.htwg.scala.exercises.Monkey;
import de.htwg.scala.koans.KoanSuite

class Monkey

class Ex46_Manifests extends KoanSuite {
  koan("""Manifests can be used to determine a type used
         |   before it erased by the VM by using an implicit manifest argument.""") {
    def inspect[T](l: List[T])(implicit manifest: scala.reflect.Manifest[T]) = manifest.toString
    val list = 1 :: 2 :: 3 :: 4 :: 5 :: Nil
    inspect(list) should be("Int")
  }

  koan("""Manifests can be attached to classes. Manifests have other meta-information about
          |  the type erased""") {
    class Barrel[T](implicit m: scala.reflect.Manifest[T]) {
      def +(t: T) = "1 %s has been added".format(m.runtimeClass.getSimpleName) //Simple-name of the class erased
    }
    val monkeyBarrel = new Barrel[Monkey]
    (monkeyBarrel + new Monkey) should be("1 Monkey has been added")
  }
}
