import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class JacksonEncoders {

    private static ObjectMapper encoder =
            new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    static class Customer {
        public String name;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        public LocalDate created;

        public Customer(String name, LocalDate created) {
            this.name = name;
            this.created = created;
        }
    }

    static class Order {

        public Optional<Status> order;

        public static class Status {
            public String status;
            public LocalDate statusDate;
        }
    }

    public static void main(String[] args) throws IOException {
        var encoded = encoder.writeValueAsString(new Customer(
                "upd",
                LocalDate.now()
        ));

        System.out.println(encoded);

        var jsonJust = "{\"order\":{\"status\":\"delivered\",\"statusDate\":\"2019-07-02\"}}";
        var deccoded = encoder.readValue(jsonJust, Order.class);
        System.out.println(deccoded.order.map(a -> a.status));

        var jsonNothing = "{\"order\": {}}";
        var decoded2 = encoder.readValue(jsonNothing, Order.class);
        System.out.println(decoded2.order.map($ -> $.status));
    }
}
