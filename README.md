# SASS plugin for sbt

[![Build Status](https://travis-ci.org/madoushi/sbt-sass.svg?branch=master)](https://travis-ci.org/madoushi/sbt-sass)

A sbt-plugin that enables you to use [SASS](http://sass-lang.com/) in your
project.

This plugin is based on [sbt-sass](https://github.com/ShaggyYeti/sbt-sass)
which is based on [play-sass](https://github.com/jlitola/play-sass). Both
plugins seem to be abandoned. Therefore we decided to create this project.

## Prerequisites

A sass compiler needs to be installed for the plugin to work. This means
that the `sass` executable needs to be found in path. Sass can be installed
by installing sass ruby gem (**minimal version 3.4.0**).

    % gem install sass

You can verify that sass has been installed by following command:

    % sass -v

Also you should install (opitonal) compass if you want to use it:

    % gem install compass

## Usage


