name := "serialisers-deserialisers"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.4"

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.4"

libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.9.4"

val circeVersion = "0.9.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
