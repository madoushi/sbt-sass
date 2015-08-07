package org.madoushi.sbt.sass

import com.typesafe.sbt.web.SbtWeb
import sbt.{AllRequirements, PluginTrigger, Plugins, AutoPlugin}

object SbtSass extends AutoPlugin {

  override def requires: Plugins = SbtWeb

  override def trigger: PluginTrigger = AllRequirements

}