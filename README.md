# SASS plugin for sbt

[![Build Status](https://travis-ci.org/madoushi/sbt-sass.svg?branch=master)](https://travis-ci.org/madoushi/sbt-sass)
[![Download](https://api.bintray.com/packages/madoushi/sbt-plugins/sbt-sass/images/download.svg)](https://bintray.com/madoushi/sbt-plugins/sbt-sass/_latestVersion)

A sbt-plugin that enables you to use [SASS](http://sass-lang.com/) in your
project.

This plugin is based on [sbt-sass](https://github.com/ShaggyYeti/sbt-sass)
which is based on [play-sass](https://github.com/jlitola/play-sass). Both
plugins seem to be abandoned. Therefore we decided to create this project.

The initial development of this plugin was sponsored by the [Wegtam GmbH](http://www.wegtam.org).

## Prerequisites

A sass compiler needs to be installed for the plugin to work. This means
that the `sass` executable needs to be found in path. Sass can be installed
by installing sass ruby gem (**minimal version 3.4.0**).

    % gem install sass

You can verify that sass has been installed by following command:

    % sass -v

Also you should install (opitonal) compass if you want to use it:

    % gem install compass

## Compatibility

The plugin is based upon the [sbt-web](https://github.com/sbt/sbt-web) project
therefore it should be compatible with any sbt project using sbt 0.13.8 or
higher and with any version of the Play Framework 2.3 or higher.

## Usage

We follow the conventions of the sbt-web project regarding the
[directory layout](https://github.com/sbt/sbt-web#file-directory-layout). To simplify things
it is recommended to just use the directory `app/assets/css` for sass files in projects using
the Play Framework.

Files starting with an underscore (`_`) are not compiled into css files. They can of course be
referenced from other sass files via an `@import` directive.

### Installation

Add the madoushi sbt-plugins repository at bintray to your resolvers in `build.sbt`:

    resolvers += "Madoushi sbt-plugins" at "https://dl.bintray.com/madoushi/sbt-plugins/"

Now you can include the plugin in `project/plugins.sbt` or `project/sbt-sass.sbt` like this:

    addSbtPlugin("org.madoushi.sbt" % "sbt-sass" % "VERSION")

You should use the current stable version for `VERSION`.

### Options

Some options can be set for the plugin and the `sass` executable.

#### sassExecutable

This options tells the plugin the exact location of the `sass` program. If it is available from `PATH` this option doesn't have to be set.

    sassExecutable in Assets := List("/home/user/.rbenv/shims/sass")

#### sassOptions

To pass additonal options to `sass` this setting can be used. To use `compass` for example use the following:

    sassOptions in Assets ++= Seq("--compass", "-r", "compass")

#### sassCss / sassCssMinified

Use these settings to override the command line arguments used for generating css / minified css
(or set them to `None` in order to skip the corresponding task).
For example, if you want use `node-sass` instead of `sass`, you can use these settings:

    sassCss in Assets := Some( (file) => Seq("-o", file.output.getParentFile.getAbsolutePath, file.input.getAbsolutePath) ),
    sassCssMinified in Assets := None

## Development

Please refer to the [contributing](CONTRIBUTING.md) guide.

