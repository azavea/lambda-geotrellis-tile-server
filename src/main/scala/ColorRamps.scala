package io.github.jisantuc.gtlambda.lambda

import geotrellis.raster.render.ColorRamp
import geotrellis.raster.render.ColorRamps._

object ColorOptions {
  lazy val default = Viridis

  def fromString(s: String): Option[ColorRamp] =
    s.toLowerCase match {
      case "viridis" => Some(Viridis)
      case "magma" => Some(Magma)
      case "inferno" => Some(Inferno)
      case "plasma" => Some(Plasma)
      case "blor" => Some(BlueToOrange)
      case "ylor" => Some(LightYellowToOrange)
      case "blrd" => Some(BlueToRed)
      case "gror" => Some(GreenToRedOrange)
      case "sunsetdark" => Some(LightToDarkSunset)
      case "greens" => Some(LightToDarkGreen)
      case "ylrd" => Some(HeatmapYellowToRed)
      case "blylrd" => Some(HeatmapBlueToYellowToRedSpectrum)
      case "rdylwt" => Some(HeatmapDarkRedToYellowWhite)
      case "prwt" => Some(HeatmapLightPurpleToDarkPurpleToWhite)
      case "landuse" => Some(ClassificationBoldLandUse)
      case "terrain" => Some(ClassificationMutedTerrain)
      case "rgb" => None
      case _ => Some(default)
    }
}
