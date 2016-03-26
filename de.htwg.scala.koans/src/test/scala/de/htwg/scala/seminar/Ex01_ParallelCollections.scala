package de.htwg.scala.seminar

import de.htwg.scala.koans.KoanSuite

import scala.collection.parallel.ForkJoinTaskSupport
import scala.collection.parallel.immutable.ParVector
import scala.concurrent.forkjoin.ForkJoinPool

class Ex01_ParallelCollections extends KoanSuite {

  koan("A sequential collection can be converted to its parallel counterpart (same name with the prefix \"Par\") using the par method.") {
    val seqVec = Vector(1, 2, 3)
    seqVec.par should be(ParVector(1, 2, 3))
  }

  koan("Some sequential collections do not have a direct parallel counterpart, so they will be copied into a similar parallel data structure.") {
    val seqList = List(1, 2, 3)
    seqList.par should be(ParVector(1, 2, 3))
  }

  koan("The seq method converts a parallel collection to its sequential counterpart") {
    List(1, 2, 3).par.seq should be(Vector(1, 2, 3))
  }

  koan("Parallel collections can be used the same way as sequential collections are used") {
    val seqVec = Vector(1, 2, 3)
    val parVec = ParVector(1, 2, 3)

    seqVec.filter(_ > 1).toSeq == parVec.filter(_ > 1).toSeq should be(true)

    seqVec.map(_ * 2) should be(Vector(2, 4, 6))
    parVec.map(_ * 2) should be(ParVector(2, 4, 6))
  }

  koan("Be careful when using functions with side effects! You will run into race conditions and the result will no longer be deterministic.") {
    // 1 + 2 + 3 + ... + 10000 = 50005000

    val seqRange = 1 to 10000
    var seqSum = 0
    seqRange.foreach(seqSum += _)

    val parRange = seqRange.par
    var parSum = 0
    parRange.foreach(parSum += _)

    seqSum should be(50005000)
    parSum == seqSum should be(false)
  }

  koan("Also, the order in which operations are executed may vary when using parallel collections. So, non-associative operations, like subtractions, will most likely be executed wrong.") {
    // 0 - 1 - 2 - 3 - ... - 10000 = -50005000

    val seqRange = 0 to 10000
    val seqSub = seqRange.reduce(_ - _)

    val parRange = seqRange.par
    val parSub = parRange.reduce(_ - _)

    seqSub should be(-50005000)
    parSub == seqSub should be(false)
  }

  koan("However, non-commutative operations are fine. The combiners of the parallel collection will preserve the order.") {
    val seqStrVec = Vector("abcdef", "ghi", "jklmno", "pqrstuvwx", "yz")
    val seqStr = seqStrVec.reduce(_ ++ _)

    val parStrVec = seqStrVec.par
    val parStr = parStrVec.reduce(_ ++ _)

    parStr should be("abcdefghijklmnopqrstuvwxyz")
    parStr == seqStr should be(true)
  }

  koan("When instantiating your own ForkJoinTaskSupport for a parallel collection, you can set the parallelization level for yourself.") {
    val parRange = (0 to 10000).par

    var sumOne = 0
    parRange.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(8))
    parRange.foreach(sumOne += _)

    var sumTwo = 0
    parRange.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(1))
    parRange.foreach(sumTwo += _)

    sumOne == 50005000 should be(false)
    sumTwo == 50005000 should be(true)
  }

}
