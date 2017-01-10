package org.madoushi.sbt.sass

import java.io.File

import org.apache.commons.lang3.SystemUtils

import scala.sys.process._

object SassCompiler {
  // The actual `sass` command depending on the operating system.
  lazy val command =
    if(SystemUtils.IS_OS_WINDOWS)
      Seq("cmd", "/c", "sass.bat")
    else
      Seq("sass")

  lazy val defaultSassCssOptions = Some( (file:SassFileInfo) => Seq("-l","-I",file.parentPath) ++ Seq(Seq(file.input.getAbsolutePath, ":", file.output.getAbsolutePath).mkString) )

  lazy val defaultSassCssMinifiedOptions = Some( (file:SassFileInfo) => Seq("-t","-I",file.parentPath) ++ Seq(Seq(file.input.getAbsolutePath, ":", file.output.getAbsolutePath).mkString) )


  case class SassFileInfo(input: File, output: File, parentPath: String)

  /**
   * A wrapper for `compile` that uses the default `sass` executable from the path.
   *
   * @param input          The input file containing SASS code.
   * @param output         The output file for the generated CSS.
   * @param outputMinified The output file for the generated and minified CSS.
   * @param options        Additional options for the `sass` executable.
   * @return A list of filenames that were used to generate the css e.g. dependencies.
   */
  def compileWithDefaultSass(input: File, output: File, outputMinified: File, options: Seq[String] = Seq.empty[String]) =
    compileWithDefaultSassOptions(command, input, output, outputMinified, options)

  /**
   * Compile the given input file into a css and a minified css file.
   * Additional options for the `sass` executable may be passed in via `options`.
   * It returns a list of scss source files that were used. Which may be
   * interesting if includes were used.
   *
   * @param sassExecutable The path to the `sass` executable.
   * @param input          The input file containing SASS code.
   * @param output         The output file for the generated CSS.
   * @param outputMinified The output file for the generated and minified CSS.
   * @param options        Additional options for the `sass` executable.
   * @return A list of filenames that were used to generate the css e.g. dependencies.
   */
  def compileWithDefaultSassOptions(sassExecutable: Seq[String], input: File, output: File, outputMinified: File, options: Seq[String] = Seq.empty[String]): Seq[String] = 
    compile(sassExecutable,input,output,outputMinified,defaultSassCssOptions,defaultSassCssMinifiedOptions,options)


  /**
   * Compile the given input file into a css and a minified css file.
   * Additional options for the `sass` executable may be passed in via `options`.
   * It returns a list of scss source files that were used. Which may be
   * interesting if includes were used.
   *
   * @param sassExecutable The path to the `sass` executable.
   * @param input          The input file containing SASS code.
   * @param output         The output file for the generated CSS.
   * @param outputMinified The output file for the generated and minified CSS.
   * @param sassCssOptions Command line options to be used for the sass command; set to None to disable this step.
   * @param sassCssMinifiedOptions Command line options to be used for the sass command to generate minified css; set to None to disable this step.
   * @param options        Additional options for the `sass` executable.
   * @return A list of filenames that were used to generate the css e.g. dependencies.
   */
  def compile(sassExecutable: Seq[String], input: File, output: File, outputMinified: File, sassCssOptions: Option[SassFileInfo=>Seq[String]],
    sassCssMinifiedOptions: Option[SassFileInfo=>Seq[String]], options: Seq[String] = Seq.empty[String]): Seq[String] = {
    if (input.getParentFile == null)
      throw new SassCompilerException(Vector(s"No parent file return for $input!"))

    val parentPath = input.getParentFile.getAbsolutePath
    val fileInfo = SassFileInfo(input,output,parentPath)

    sassCssOptions foreach { cmdOptions =>
      runCommand(
        sassExecutable ++ cmdOptions(fileInfo) ++ options
      )
    }

    sassCssMinifiedOptions foreach { cmdOptions =>
      runCommand(
        sassExecutable ++ cmdOptions(fileInfo) ++ options 
      )
    }

    extractDependencies(output)
  }

  // Regular expression to extract sass dependency comments from generated css.
  lazy val generatedCssDependency = "/\\* line \\d+, (.*) \\*/".r

  /**
   * Parse the given css file and extract files that are marked as dependencies
   * via comments from sass.
   *
   * @param generatedCss A file containing sass generated css.
   * @return A list of filenames.
   */
  private def extractDependencies(generatedCss: File): Seq[String] = scala.io.Source.fromFile(generatedCss).getLines().collect {
    case generatedCssDependency(dep) => dep
  }.toList.distinct

  /**
   * Execute the given command and return it's output.
   * If the process exit value does not equal zero then a `SassCompilerException` will be thrown.
   *
   * @param cmd The command to be executed.
   * @return The output of the command (stdout).
   * @throws SassCompilerException If the process termination value does not equal zero.
   */
  private def runCommand(cmd: ProcessBuilder) = {
    val stdout = Vector.newBuilder[String]
    val stderr = Vector.newBuilder[String]

    val captureOutput = ProcessLogger(
      line => stdout += line,
      line => stderr += line
    )

    val proc = cmd.run(captureOutput)
    if (proc.exitValue() != 0) {
      throw new SassCompilerException(stderr.result())
    }
    stdout.result()
  }
}
