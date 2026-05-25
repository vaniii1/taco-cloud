package ihromovyi.tacocloud.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record CartItemResponseDto(
        @JsonProperty("taco_id")
        Long tacoId,
        @JsonProperty("taco_name")
        String tacoName,
        Integer quantity,
        BigDecimal price
) {
}
