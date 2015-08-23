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

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4" withJavadoc()

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % "test"

// Publishing options
// ==================
bintrayOrganization := Option("madoushi")
bintrayPackageLabels := Seq("sbt", "sbt-plugin", "sass")
bintrayReleaseOnPublish in ThisBuild := false
bintrayRepository := "sbt-plugins"
publishMavenStyle := false
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
