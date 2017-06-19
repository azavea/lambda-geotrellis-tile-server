Lambda Geotrellis Tile Server
======

Deployment
------

- Configure your AWS profile
- `./scripts/publish`
- Configure your API gateway endpoint:
  - Set binary media types to `image/png` in the options for your API
  - Navigate to the `GET` endpoint and set "Content handling" to "Convert to binary (if needed)" for your resource
- Deploy the API using the `Actions` dropdown
