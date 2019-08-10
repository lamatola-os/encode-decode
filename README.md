serialise - deserialise
---------------------

1) jackson - https://github.com/FasterXML/jackson
2) circe - https://circe.github.io/circe/
3) argonaut - https://github.com/argonaut-io/argonaut TODO
4) play.json lib - https://www.playframework.com/documentation/2.6.x/ScalaJson
5) json4s - https://github.com/json4s/json4s TODO

```scala
scala> val om = new ObjectMapper
om: com.fasterxml.jackson.databind.ObjectMapper = com.fasterxml.jackson.databind.ObjectMapper@5a9adbb4

scala> val json = om.readTree("""{"config": [{ "name": "a"}, {"name": "b"}] }""")
json: com.fasterxml.jackson.databind.JsonNode = {"config":[{"name":"a"},{"name":"b"}]}

scala> json.at("/config").forEach(a => println(a.at("/name")))
"a"
"b"

scala> val json = om.readTree("""{"config": [{ "name": "a"}, {"name": "b"}, {"name": ""}] }""")
json: com.fasterxml.jackson.databind.JsonNode = {"config":[{"name":"a"},{"name":"b"},{"name":""}]}

scala> json.at("/config").forEach(a => println(a.at("/name").textValue.nonEmpty))
true
true
false
```
