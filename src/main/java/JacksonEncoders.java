import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.Customer;
import data.Order;

import java.io.IOException;
import java.time.LocalDate;

public class JacksonEncoders {

    private static ObjectMapper encoder =
            new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    public static void main(String[] args) throws IOException {
        var encoded = encoder.writeValueAsString(new Customer(
                "upd",
                "LUYATA",
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
