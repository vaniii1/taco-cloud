package ihromovyi.tacocloud.dto.order;

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
        @JsonProperty("item_ids")
        Set<Long> itemIds,
        @JsonProperty("is_deleted")
        Boolean isDeleted) {
}
