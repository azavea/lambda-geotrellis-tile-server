package io.github.jisantuc.gtlambda.lambda

import io.github.jisantuc.gtlambda.tile.EmptyRequest

import com.amazonaws.services.lambda.runtime.{Context, RequestStreamHandler}
import io.circe.Json
import io.circe.parser.decode

import java.io.{InputStream, ByteArrayOutputStream, OutputStream}
import java.nio.charset.StandardCharsets.UTF_8

class TileRequestHandler extends RequestStreamHandler {
  def handleRequest(input: InputStream, out: OutputStream, context: Context): Unit = {
    val payload = scala.io.Source.fromInputStream(input).mkString("")
    val logger = context.getLogger()
    logger.log(s"$payload")
    val decoded = decode[EmptyRequest](payload) match {
      case Right(req) => req.toTile.renderPng.bytes
      case Left(_) => throw new Exception("lol")
    }
    out.write(decoded)
  }
}
