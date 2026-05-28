package ihromovyi.tacocloud.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import ihromovyi.tacocloud.model.Payment;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        BigDecimal amount,
        Payment.Status status,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("last_modified_at")
        LocalDateTime lastModifiedAt
) {
}
