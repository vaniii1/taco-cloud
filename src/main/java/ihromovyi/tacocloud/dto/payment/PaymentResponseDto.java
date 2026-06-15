package ihromovyi.tacocloud.dto.payment;

import ihromovyi.tacocloud.model.Payment;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long userId,
        Long orderId,
        BigDecimal amount,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        Payment.Status status) {
}
