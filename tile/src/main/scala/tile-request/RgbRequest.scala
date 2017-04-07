package com.jisantuc.gtlambda.requests

case class RgbRequest (
  x: Int,
  y: Int,
  z: Int,
  catalog: String
) extends Request
