package ihromovyi.tacocloud.dto.payment;

import ihromovyi.tacocloud.exception.InvalidStatusException;
import ihromovyi.tacocloud.model.Payment;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;

public record PaymentStatusDto(
        @NotNull
        String status
) {
    public Payment.Status toStatus() {
        try {
            return Payment.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(
                    "Invalid status: '" + status + "'. Valid values: "
                            + Arrays.toString(Payment.Status.values()));
        }
    }
}
