import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonOrder {

    public static void main(String[] args) throws JsonProcessingException {
        var defaultEncoder = new ObjectMapper();
        var alphabeticEncoder = new ObjectMapper().configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        var json = "{\"d\":4,\"c\":3,\"e\":5,\"b\":2,\"a\":1,\"j\":6,\"i\":6,\"h\":6,\"g\":{\"b\":2,\"a\":1}}";
        var orderedDec = defaultEncoder.readValue(json, LinkedHashMap.class);
        var orderedEn = defaultEncoder.writeValueAsString(orderedDec);
        System.out.println(orderedEn);

        System.out.println("-----");
        var randomOrderedDec = defaultEncoder.readValue(json, Map.class);
        var randomEn = defaultEncoder.writeValueAsString(randomOrderedDec);
        System.out.println(randomEn);

        System.out.println("------");
        System.out.println(orderedEn.equals(randomEn));

        //JSONObject is super random
        var json1 = new JSONObject(json);
        System.out.println(json1.toString());

        //alphabetically decode
        System.out.println("-------");
        var alphDec = alphabeticEncoder.readValue(json, Map.class);
        System.out.println(alphabeticEncoder.writeValueAsString(alphDec));
    }
}
