encode / decode
---------------------

| library   |  link                                |
|-----------|--------------------------------------|
| jackson |  https://github.com/FasterXML/jackson |
| circe | https://circe.github.io/circe/ |
| argonaut | https://github.com/argonaut-io/argonaut |
| play.json lib |  https://www.playframework.com/documentation/2.6.x/ScalaJson |
| json4s | https://github.com/json4s/json4s |



```java
// find ~/.m2/repository/ -name "*.jar" | tr '\n' ':'

jshell --class-path $(find ~/.m2/repository/com/fasterxml/ -name "*.jar" | tr '\n' ':') --enable-preview
|  Welcome to JShell -- Version 12.0.1
|  For an introduction type: /help intro

jshell> import com.fasterxml.jackson.databind.ObjectMapper

jshell> var om = new ObjectMapper()
om ==> com.fasterxml.jackson.databind.ObjectMapper@30a3107a

jshell> import com.fasterxml.jackson.core.*
jshell> var json = om.readTree("{\"config\": [{ \"name\": \"a\"}, {\"name\": \"b\"}] }")
json ==> {"config":[{"name":"a"},{"name":"b"}]}

jshell> import com.fasterxml.jackson.core.JsonPointer
jshell> json.at("/config")
$12 ==> [{"name":"a"},{"name":"b"}]

jshell> json.at("/config").forEach(v -> System.out.println(v))
{"name":"a"}
{"name":"b"}
```

```java
scala> val json = om.readTree("""{"config": [{ "name": "a"}, {"name": "b"}, {"name": ""}] }""")
json: com.fasterxml.jackson.databind.JsonNode = {"config":[{"name":"a"},{"name":"b"},{"name":""}]}

scala> json.at("/config").forEach(a => println(a.at("/name").textValue.nonEmpty))
true
true
false
```


proto
--

```bash
brew install protobuf
brew upgrade protobuf

protoc src/main/resources/sku.proto --java_out=src/main/java/data/
```

Note
-----

- install https://github.com/mplushnikov/lombok-intellij-plugin for idea 2019.3.+