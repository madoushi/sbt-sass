package org.madoushi.sbt.sass

import java.io.File

import org.scalatest.{FunSpec, MustMatchers}

class SassCompilerTest extends FunSpec with MustMatchers {
  describe("SassCompiler") {
    describe("using well formed scss input") {
      describe("without includes") {
        it("should compile") {
          val input = new File(getClass.getResource("/org/madoushi/sbt/sass/well-formed.scss").toURI)
          val output = File.createTempFile("sbt-sass-test", ".css")
          val outputMinified = File.createTempFile("sbt-sass-test", ".min.css")

          val processOutput = SassCompiler.compile(input, output, outputMinified)

          val css = scala.io.Source.fromFile(output).mkString
          val cssMin = scala.io.Source.fromFile(outputMinified).mkString

          css.length must be > cssMin.length

          val testCss = css.replaceAll("\\/\\*.*?\\*\\/", "").replaceAll("\\s+", "")
          testCss must include(".test{font-size:10px;}")
          testCss must include(".test.hidden{display:none;}")

          processOutput.size must be(1)
          processOutput.head must include("well-formed.scss")
        }
      }

      describe("with includes") {
        it("should compile") {
          val input = new File(getClass.getResource("/org/madoushi/sbt/sass/well-formed-using-import.scss").toURI)
          val output = File.createTempFile("sbt-sass-test", ".css")
          val outputMinified = File.createTempFile("sbt-sass-test", ".min.css")

          val processOutput = SassCompiler.compile(input, output, outputMinified)

          val css = scala.io.Source.fromFile(output).mkString
          val cssMin = scala.io.Source.fromFile(outputMinified).mkString

          css.length must be > cssMin.length

          val testCss = css.replaceAll("\\/\\*.*?\\*\\/", "").replaceAll("\\s+", "")
          testCss must include(".test-import{font-weight:bold;}")
          testCss must include(".test{font-size:10px;}")
          testCss must include(".test.hidden{display:none;}")

          processOutput.size must be(2)
          println(processOutput)
          processOutput.find(_.contains("_well-formed-import.scss")) must not be None
        }
      }
    }

    describe("using broken scss input") {
      it("should throw an exception") {
        val input = new File(getClass.getResource("/org/madoushi/sbt/sass/broken-input.scss").toURI)
        val output = File.createTempFile("sbt-sass-test", ".css")
        val outputMinified = File.createTempFile("sbt-sass-test", ".min.css")

        val exception = the [SassCompilerException] thrownBy SassCompiler.compile(input, output, outputMinified)
        exception.getMessage must include("Invalid CSS after")
      }
    }
  }
}
