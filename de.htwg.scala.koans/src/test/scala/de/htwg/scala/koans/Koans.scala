package de.htwg.scala.koans

import org.scalatest._
import support.Master
import scala.collection.immutable.IndexedSeq

class Koans extends Suite {
  override def nestedSuites = IndexedSeq[Suite](
    new AboutAsserts,
    new AboutValAndVar,
    new AboutClasses,
    new AboutObjects,

    new AboutTuples,
    new AboutLists,
    new AboutMaps,
    new AboutSets,
    new AboutOptions,
    new AboutMutableMaps,
    new AboutMutableSets,
    new AboutFormatting,
    new AboutPatternMatching,
    new AboutCaseClasses,

    new AboutRange,
    new AboutHigherOrderFunctions,
    new AboutPartiallyAppliedFunctions,
    new AboutPartialFunctions,

    //Afternoon
    new AboutImplicits,
    new AboutTraits,
    new AboutForExpressions,
    new AboutInfixPrefixAndPostfixOperators,
    new AboutInfixTypes,

    new AboutSequencesAndArrays,
    new AboutIterables,
    new AboutTraversables,
    new AboutNamedAndDefaultArguments,
    new AboutManifests,
    new AboutPreconditions,

    //move this later type variance and signatures on slides and REPL

    new AboutParentClasses,

    new AboutEmptyValues,

    new AboutTypeSignatures,
    new AboutUniformAccessPrinciple,
    new AboutLiteralBooleans,
    new AboutLiteralNumbers,
    new AboutLiteralStrings,
    new AboutTypeVariance,
    new AboutEnumerations,
    new AboutConstructors
  )

  override def run(testName: Option[String], args:Args):Status = {
    super.run(testName, args)
  }

}
