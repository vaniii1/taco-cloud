package ihromovyi.tacocloud.dto.tacoorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Set;

public record TacoOrderResponseDto(
        Long id,
        @JsonProperty("delivery_name")
        String deliveryName,
        @JsonProperty("delivery_street")
        String deliveryStreet,
        @JsonProperty("delivery_city")
        String deliveryCity,
        @JsonProperty("delivery_state")
        String deliveryState,
        @JsonProperty("delivery_zip")
        String deliveryZip,
        @JsonProperty("placed_at")
        Date placedAt,
        @JsonProperty("taco_ids")
        Set<Long> tacoIds) {
}
