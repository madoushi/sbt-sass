name := "sbt-sass"
organization := "org.madoushi.sbt"
version := "2.0.0"

sbtPlugin := true

scalaVersion := "2.12.4"
scalacOptions ++= Seq(
  "-unchecked",
  "-Xlint",
  "-deprecation",
  "-Xfatal-warnings",
  "-feature",
  "-encoding", "UTF-8"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.3")

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.7" withJavadoc()

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

// Publishing options
// ==================
bintrayOrganization := Option("madoushi")
bintrayPackageLabels := Seq("sbt", "sbt-plugin", "sass")
bintrayReleaseOnPublish in ThisBuild := false
bintrayRepository := "sbt-plugins"
publishMavenStyle := false
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
publish := (publish dependsOn (test in Test)).value

