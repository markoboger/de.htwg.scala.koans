package de.htwg.scala.exercises

import org.scalatest._
import de.htwg.scala.koans.Master
import scala.collection.immutable.IndexedSeq
import de.htwg.scala.exercises.Ex01_Asserts;
import de.htwg.scala.exercises.Ex02_ValAndVar;
import de.htwg.scala.exercises.Ex03_LiteralBooleans;
import de.htwg.scala.exercises.Ex04_LiteralNumbers;
import de.htwg.scala.exercises.Ex05_LiteralStrings;
import de.htwg.scala.exercises.Ex06_InfixPrefixAndPostfixOperators;
import de.htwg.scala.exercises.Ex07_Formatting;
import de.htwg.scala.exercises.Ex08_Enumerations;
import de.htwg.scala.exercises.Ex09_Lists;
import de.htwg.scala.exercises.Ex10_Sets;
import de.htwg.scala.exercises.Ex11_Maps;
import de.htwg.scala.exercises.Ex12_MutableSets;
import de.htwg.scala.exercises.Ex13_MutableMaps;
import de.htwg.scala.exercises.Ex14_Range;
import de.htwg.scala.exercises.Ex15_ForExpressions;
import de.htwg.scala.exercises.Ex16_Tuples;
import de.htwg.scala.exercises.Ex17_Options;
import de.htwg.scala.exercises.Ex18_EmptyValues;
import de.htwg.scala.exercises.Ex19_SequencesAndArrays;
import de.htwg.scala.exercises.Ex20_Iterables;
import de.htwg.scala.exercises.Ex21_Traversables;
import de.htwg.scala.exercises.Ex22_CaseClasses;
import de.htwg.scala.exercises.Ex23_PatternMatching;
import de.htwg.scala.exercises.Ex24_Classes;
import de.htwg.scala.exercises.Ex26_Traits;
import de.htwg.scala.exercises.Ex27_Constructors;
import de.htwg.scala.exercises.Ex28_NamedAndDefaultArguments;
import de.htwg.scala.exercises.Ex29_ParentClasses;
import de.htwg.scala.exercises.Ex29_Preconditions;
import de.htwg.scala.exercises.Ex35_UniformAccessPrinciple;
import de.htwg.scala.exercises.Ex36_PartialFunctions;
import de.htwg.scala.exercises.Ex37_HigherOrderFunctions;
import de.htwg.scala.exercises.Ex38_PartiallyAppliedFunctions;
import de.htwg.scala.exercises.Ex39_InfixTypes;
import de.htwg.scala.exercises.Ex41_TypeSignatures;
import de.htwg.scala.exercises.Ex45_Implicits;
import de.htwg.scala.exercises.Ex46_Manifests;
import de.htwg.scala.exercises.Ex25_Objects

class ScalaExerciseKoanSuite extends Suite {
  override def nestedSuites = IndexedSeq[Suite](
    new Ex01_Asserts,
    new Ex02_ValAndVar,
    new Ex24_Classes,
    new Ex25_Objects,

    new Ex16_Tuples,
    new Ex09_Lists,
    new Ex11_Maps,
    new Ex10_Sets,
    new Ex17_Options,
    new Ex13_MutableMaps,
    new Ex12_MutableSets,
    new Ex07_Formatting,
    new Ex23_PatternMatching,
    new Ex22_CaseClasses,

    new Ex14_Range,
    new Ex37_HigherOrderFunctions,
    new Ex38_PartiallyAppliedFunctions,
    new Ex36_PartialFunctions,

    new Ex45_Implicits,
    new Ex26_Traits,
    new Ex15_ForExpressions,
    new Ex06_InfixPrefixAndPostfixOperators,
    new Ex39_InfixTypes,

    new Ex19_SequencesAndArrays,
    new Ex20_Iterables,
    new Ex21_Traversables,
    new Ex28_NamedAndDefaultArguments,
    new Ex46_Manifests,
    new Ex29_Preconditions,

    new Ex29_ParentClasses,

    new Ex18_EmptyValues,

    new Ex41_TypeSignatures,
    new Ex35_UniformAccessPrinciple,
    new Ex03_LiteralBooleans,
    new Ex04_LiteralNumbers,
    new Ex05_LiteralStrings,
    new Ex47_TypeVariance,
    new Ex08_Enumerations,
    new Ex27_Constructors
  )

  override def run(testName: Option[String], args:Args):Status = {
    super.run(testName, args)
  }

}
