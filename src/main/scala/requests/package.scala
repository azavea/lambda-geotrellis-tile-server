package io.github.jisantuc.gtlambda

import geotrellis.raster.{Tile, MultibandTile}
import geotrellis.raster.render.{ColorRamp, ColorMap}

import com.amazonaws.services.lambda.runtime.LambdaLogger

package object tile {
  abstract class Request {
    val x: Int
    val y: Int
    val z: Int
    def toTile(implicit logger: LambdaLogger): Tile
    def toMultibandTile(implicit logger: LambdaLogger): MultibandTile
  }
}
