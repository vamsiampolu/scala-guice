organization := "com.example",
scalaVersion := "2.12.6",
version      := "0.1.0-SNAPSHOT"
name := "scala-guice",

libraryDependencies += Seq(
  "com.google.inject" % "guice" % "4.1.0",
   "org.specs2" %% "specs2-core" % "4.2.0" % Test,
    "org.specs2" %% "specs2-mock" % "4.2.0" % Test
)
