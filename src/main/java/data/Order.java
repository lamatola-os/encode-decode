package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    private Optional<Status> order;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Status {
        private String status;
        private LocalDate statusDate;
    }
}
