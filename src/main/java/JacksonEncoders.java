import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.Customer;
import data.Order;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JacksonEncoders {

    private static ObjectMapper encoder =
            new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    public static void main(String[] args) throws IOException {
        var encoded = encoder.writeValueAsString(new Customer(
                "upd",
                "LUYATA",
                LocalDate.now(),
                LocalDateTime.now(),
                LocalTime.now()
        ));

        System.out.println(encoded);

        var jsonJust = "{\"order\":{\"status\":\"delivered\",\"statusDate\":\"2019-07-02\"}}";
        var deccoded = encoder.readValue(jsonJust, Order.class);
        System.out.println(deccoded.getOrder().map(a -> a.getStatus()));

        var jsonNothing = "{\"order\": {}}";
        var decoded2 = encoder.readValue(jsonNothing, Order.class);
        System.out.println(decoded2.getOrder().map($ -> $.getStatus()));
    }
}
