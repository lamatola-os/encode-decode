name := "encoder-decoder"

version := "0.1"

scalaVersion := "2.12.8"

val JacksonEncoderVersion = "2.10.0.pr1"
val circeVersion = "0.10.0"

////
////

libraryDependencies ++= Seq(
    "com.fasterxml.jackson.core" % "jackson-core" % JacksonEncoderVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % JacksonEncoderVersion,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % JacksonEncoderVersion
)

//
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

////
////
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.0-M1"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
