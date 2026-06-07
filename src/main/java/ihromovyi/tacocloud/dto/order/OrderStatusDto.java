package ihromovyi.tacocloud.dto.order;

import ihromovyi.tacocloud.exception.InvalidStatusException;
import ihromovyi.tacocloud.model.Order;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;

public record OrderStatusDto(
        @NotNull
        String status
) {
    public Order.Status toStatus() {
        try {
            return Order.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(
                    "Invalid status: '" + status + "'. Valid values: "
                            + Arrays.toString(Order.Status.values()));
        }
    }

}
