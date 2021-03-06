name:="de.htwg.scala.koans"

version:="1.0"

scalaVersion:="2.11.7"

resolvers += "lenny2" at "http://lenny2.in.htwg-konstanz.de:8081/artifactory/libs-release-local"

libraryDependencies += "org.codetask" % "koanlib" % "0.1.1" 

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "org.spire-math" %% "spire" % "0.11.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.4"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.4.4"

libraryDependencies += "com.typesafe.akka" %% "akka-http-core-experimental" % "2.0.4"

libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4"

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.4"

libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit-experimental" % "2.0.4"

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.1.1"

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.10"

libraryDependencies += "com.h2database" % "h2" % "1.4.187"
