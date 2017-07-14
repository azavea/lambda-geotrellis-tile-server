service: lambda-tile-server

provider:
  name: aws
  runtime: java8
  timeout: 30
  memorySize: 512

# you can overwrite defaults here
#  stage: dev
#  region: us-east-1

# you can define service wide environment variables here
#  environment:
#    variable1: value1

package:
  artifact: target/scala-2.11/lambda-geotrellis-tile-server.jar

functions:
  getTile:
    handler: io.github.jisantuc.gtlambda.lambda.TileRequestHandler
    events:
      - http:
          path: tile/{b}/{p}/{l}/{v}/{z}/{x}/{y}/
          method: get
          response:
            headers:
              Content-Type: "'image/png'"
          integration: lambda
          cors: true
          request:
            template:
              application/json: '{ }'
            parameters:
              paths:
                z: 0
                y: 0
                x: 0
                b: none
                p: none
                l: none
                v: viridis
            headers:
              Content-Type: application/json
