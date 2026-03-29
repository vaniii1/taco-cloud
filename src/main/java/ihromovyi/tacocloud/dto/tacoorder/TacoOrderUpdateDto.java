package ihromovyi.tacocloud.dto.tacoorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public record TacoOrderUpdateDto(
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
        @JsonProperty("taco_ids")
        Set<Long> tacoIds,
        @JsonProperty("is_deleted")
        Boolean isDeleted) {
}
