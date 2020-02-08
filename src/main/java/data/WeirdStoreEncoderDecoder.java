package data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class WeirdStoreEncoderDecoder {

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new WeirdStoreModule());

    /**
     * {
     *   "bajhang store": {
     *     "properties": {
     *       "tags": [
     *         "store"
     *       ],
     *       "address": "yata"
     *     }
     *   }
     * }
     */
    private static class WeirdStoreEncoder extends JsonSerializer<WeirdStore> {

        @Override
        public void serialize(WeirdStore store,
                              JsonGenerator gen,
                              SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField(store.getName(), Map.of(
                    "properties", Map.of(
                            "address", store.getAddress(),
                            "tags", store.getTags()
                    )
            ));
            gen.writeEndObject();
        }
    }

    private static class WeirdStoreDecoder extends JsonDeserializer<WeirdStore> {

        @Override
        public WeirdStore deserialize(JsonParser p,
                                  DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            JsonNode json = p.getCodec().readTree(p);
            String storeName = json.fieldNames().next();
            JsonNode properties = json.get(storeName).get("properties");

            var tags = toArrayList(properties.withArray("tags").elements())
                    .stream().map(JsonNode::asText)
                    .collect(Collectors.toList());

            return new WeirdStore(
                    storeName,
                    properties.get("address").asText(),
                    tags
            );
        }
    }

    public static <T> ArrayList<T> toArrayList(final Iterator<T> iterator) {
        return StreamSupport
                .stream(
                        Spliterators
                                .spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static class WeirdStoreModule extends SimpleModule {

        public WeirdStoreModule() {
            addSerializer(WeirdStore.class, new WeirdStoreEncoder());
            addDeserializer(WeirdStore.class, new WeirdStoreDecoder());
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        var store = new WeirdStore(
                "bajhang store",
                "yata",
                List.of("store", "love")
        );

        var jsonEncoded = objectMapper.writeValueAsString(store);

        System.out.println(jsonEncoded);

        var decoded  =  objectMapper.readValue(jsonEncoded, WeirdStore.class);
        System.out.println(decoded);
    }
}
