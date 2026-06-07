package ihromovyi.tacocloud.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemUpdateQuantityDto(
        @NotNull
        @Min(0)
        Integer quantity
) {
}
