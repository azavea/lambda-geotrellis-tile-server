Lambda Geotrellis Tile Server
======

This repository creates a geotrellis tile server on AWS's API Gateway service, backed by AWS Lambda.
The resulting url pattern is:

`https://<aws url>/tile/{b}/{p}/{l}/{v}/{z}/{x}/{y}/`

### Getting data

`b`, `p`, and `l` specify an s3 path to a `RasterRDD`.

- `b` is the bucket
- `p` is the url-encoded prefix
- `l` is the url-encoded name of the layer

`{z}/{x}/{y}` is just normal TMS stuff.

### Visualizing your layer

The `v` parameter specifies what type of visualization you'd like. All of the ColorRamps
from `geotrellis.raster.render.ColorRamps` are available with shortened case-insensitive
string names. Those names and the corresponding `ColorRamps` are:

- `viridis`: `Viridis`
- `magma`: `Magma`
- `inferno`: `Inferno`
- `plasma`: `Plasma`
- `blor`: `BlueToOrange`
- `ylor`: `LightYellowToOrange`
- `blrd`: `BlueToRed`
- `gror`: `GreenToRedOrange`
- `sunsetdark`: `LightToDarkSunset`
- `greens`: `LightToDarkGreen`
- `ylrd`: `HeatmapYellowToRed`
- `blylrd`: `HeatmapBlueToYellowToRedSpectrum`
- `rdylwt`: `HeatmapDarkRedToYellowWhite`
- `prwt`: `HeatmapLightPurpleToDarkPurpleToWhite`
- `landuse`: `ClassificationBoldLandUse`
- `terrain`: `ClassificationMutedTerrain`

To return an RGB png, pass `rgb` as the visualization type. No promises about
what will happen if you ask for an `rgb` visualization of a single band tiff.

If you pass in any other string name, you'll get `Viridis`. You probably wanted
`Viridis` anyway, since it's so pretty.

There's currently no way to specify class breaks, so `landuse` and `terrain` currently
look pretty dumb. 

Deployment
------

*tl;dr*: `./scripts/publish`

### Longer version:

You'll need the following AWS permissions:
  - cloudformation:*
  - lambda:*
  - apigateway:*

Once that's set up for your user:

- Configure your AWS profile
- `npm install -g serverless`
- `./scripts/publish`
- Configure your API gateway endpoint from the AWS console:
  - Set binary media types to `image/png` in the options for your API
  - Navigate to the `GET` endpoint and set "Content handling" to "Convert to binary (if needed)" for your resource
- Deploy the API using the `Actions` dropdown
