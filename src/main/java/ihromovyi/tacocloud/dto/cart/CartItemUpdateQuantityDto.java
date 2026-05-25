package ihromovyi.tacocloud.dto.cart;

import jakarta.validation.constraints.Min;

public record CartItemUpdateQuantityDto(
        @Min(0)
        Integer quantity
) {
}
