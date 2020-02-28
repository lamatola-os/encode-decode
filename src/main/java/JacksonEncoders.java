import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.Customer;
import data.Order;

import java.io.IOException;
import java.time.*;

public class JacksonEncoders {

    private static ObjectMapper encoder =
            new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    public static void main(String[] args) throws IOException {
        var encodedCustomerJson = encoder.writeValueAsString(new Customer(
                "upd",
                "LUYATA",
                LocalDateTime.now(),
                ZonedDateTime.now(ZoneId.of("America/Los_Angeles")),
                LocalDate.now(),
                LocalTime.now(),
                ZoneId.of("America/Los_Angeles")
        ));
        System.out.println(encodedCustomerJson);

        //
        var decodedObj = encoder.readValue(encodedCustomerJson, Customer.class);
        System.out.println("\ndecoded customer object: \n" + decodedObj);

        var jsonJust = "{\"order\":{\"status\":\"delivered\",\"statusDate\":\"2019-07-02\"}}";
        var decoded = encoder.readValue(jsonJust, Order.class);
        System.out.println("decoded status: " + decoded.getOrder().map(a -> a.getStatus()));

        var jsonNothing = "{\"order\": {}}";
        var decoded2 = encoder.readValue(jsonNothing, Order.class);
        System.out.println("decoded status: " + decoded2.getOrder().map($ -> $.getStatus()));
    }
}
