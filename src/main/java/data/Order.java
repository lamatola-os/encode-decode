package data;

import java.time.LocalDate;
import java.util.Optional;

public class Order {

    public Optional<Status> order;

    public static class Status {
        public String status;
        public LocalDate statusDate;
    }
}
