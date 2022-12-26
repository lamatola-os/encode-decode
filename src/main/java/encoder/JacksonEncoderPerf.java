package encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.Customer;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 1 - 62ms
 * 1K - 90ms
 * 10K - 140 ms
 * 100K - 295 ms
 */
public class JacksonEncoderPerf {

    final static List<Customer> orders = IntStream.range(1, 100000)
            .mapToObj($ -> new Customer(
                    "upd",
                    "LUYATA",
                    LocalDateTime.now(),
                    ZonedDateTime.now(ZoneId.of("America/Los_Angeles")),
                    LocalDate.now(),
                    LocalTime.now(),
                    ZoneId.of("America/Los_Angeles")
            ))
            .collect(Collectors.toList());

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        var  start = System.currentTimeMillis();

        var encoded = objectMapper.writeValueAsString(orders);
        System.out.println("took: " + (System.currentTimeMillis() - start) + " ms");

        //System.out.println(encoded);
    }
}
