package io.github.jisantuc.gtlambda.lambda

import io.github.jisantuc.gtlambda.tile._

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import geotrellis.raster.render.{ColorMap, ColorRamps}
import io.circe.Json
import io.circe.optics.JsonPath._
import io.circe.parser.{parse, decode}

import java.io.{InputStream, ByteArrayOutputStream, OutputStream}
import java.util.Base64
import java.nio.charset.StandardCharsets.UTF_8

class TileRequestHandler extends RequestStreamHandler {
  def handleRequest(input: InputStream, out: OutputStream, context: Context): Unit = {
    val encoder = Base64.getEncoder()
    val payload = scala.io.Source.fromInputStream(input).mkString("")
    val payloadJson: Json = parse(payload) match {
      case Right(js) => js
      case Left(e) => throw e
    }
    val logger = context.getLogger()
    logger.log(s"$payload")
    val style = root.s.string.getOption(payloadJson).getOrElse("empty")
    logger.log(s"Style was: $style")
    val decoded = style.toLowerCase match {
      case "empty" => decode[EmptyRequest](payload)
      case _ => decode[RgbRequest](payload)
    }
    val cm = ColorMap((0 to 3500 by 100).toArray, ColorRamps.Viridis)
    val encodedBytes = decoded match {
      case Right(req) =>
        logger.log(s"Decoded class was ${req.getClass.toString}")
        val tile = req.toTile
        logger.log(s"Class of returned tile was ${tile.getClass.toString}")
        encoder.encode(tile.renderPng(cm).bytes)
      case Left(_) => throw new Exception(payload)
    }
    out.write(encodedBytes)
  }
}
