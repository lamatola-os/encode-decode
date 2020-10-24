import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.Customer;
import data.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.time.*;
import java.util.List;

public class JacksonEncoders {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class A {
        private String a;
    }

    private final static TypeReference<List<A>> AS_TYPE = new TypeReference<>() {
    };

    private static ObjectMapper encoder =
            new ObjectMapper()
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule())
                    .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    public static void main(String[] args) throws IOException {
        var encoded = encoder.writeValueAsString(new Customer(
                "upd",
                "LUYATA",
                LocalDateTime.now(),
                ZonedDateTime.now(ZoneId.of("America/Los_Angeles")),
                LocalDate.now(),
                LocalTime.now()
        ));
        System.out.println(encoded);

        //
        var decodedObj = encoder.readValue(encoded, Customer.class);
        System.out.println("decoded object: " + decodedObj);

        var jsonJust = "{\"order\":{\"status\":\"delivered\",\"statusDate\":\"2019-07-02\"}}";
        var decoded = encoder.readValue(jsonJust, Order.class);
        System.out.println(decoded.getOrder().map(a -> a.getStatus()));

        var jsonNothing = "{\"order\": {}}";
        var decoded2 = encoder.readValue(jsonNothing, Order.class);
        System.out.println(decoded2.getOrder().map($ -> $.getStatus()));

        List<A> list = encoder.readValue("[]", AS_TYPE);
        System.out.println(list);

        A a = encoder.readValue("{\"a\": \"paraya\"}", A.class);
        System.out.println(a);
    }
}
