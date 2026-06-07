package ihromovyi.tacocloud.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record CartItemResponseDto(
        @JsonProperty("item_id")
        Long id,
        @JsonProperty("taco_id")
        Long tacoId,
        @JsonProperty("taco_name")
        String tacoName,
        Integer quantity,
        BigDecimal price
) {
}
