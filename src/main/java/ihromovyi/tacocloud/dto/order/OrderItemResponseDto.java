package ihromovyi.tacocloud.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record OrderItemResponseDto(
        @JsonProperty("taco_id")
        Long tacoId,
        @JsonProperty("taco_name")
        String tacoName,
        Integer quantity,
        BigDecimal subtotal
) {
}
