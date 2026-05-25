package ihromovyi.tacocloud.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Set;

public record CartResponseDto(
        Set<CartItemResponseDto> items,
        @JsonProperty("total_price")
        BigDecimal totalPrice
) {
}
