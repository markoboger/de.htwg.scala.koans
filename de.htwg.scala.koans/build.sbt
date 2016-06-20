name:="de.htwg.scala.koans"

version:="1.0"

scalaVersion:="2.11.7"

resolvers += "lenny2" at "http://lenny2.in.htwg-konstanz.de:8081/artifactory/libs-release-local"

libraryDependencies += "org.codetask" % "koanlib" % "0.1.1" 

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "org.spire-math" %% "spire" % "0.11.0"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.4"

resolvers += "akka" at "http://repo.akka.io/snapshots"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor" % "2.4.4",					
							"com.typesafe.akka" %% "akka-remote"   % "2.4.4",
							"com.typesafe.akka" %% "akka-testkit"  % "2.4.4"% "test"
)

