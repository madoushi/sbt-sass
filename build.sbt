name := "sbt-sass"
organization := "org.madoushi.sbt"
version := "1.0.0-SNAPSHOT"

sbtPlugin := true

scalaVersion := "2.10.5"
scalacOptions ++= Seq(
  "-unchecked",
  "-Xlint",
  "-deprecation",
  "-Xfatal-warnings",
  "-feature",
  "-encoding", "UTF-8"
)
incOptions := incOptions.value.withNameHashing(nameHashing = true)

resolvers ++= List(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.2.2")

