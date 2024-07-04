ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

val catsVersion = "2.12.0"

lazy val root = (project in file("."))
  .settings(
    name := "Cats",
    libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion
  )
