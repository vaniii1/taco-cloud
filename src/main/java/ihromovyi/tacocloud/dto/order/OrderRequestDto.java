package ihromovyi.tacocloud.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDto(
        @NotNull
        @JsonProperty("delivery_name")
        String deliveryName,
        @NotNull
        @JsonProperty("delivery_street")
        String deliveryStreet,
        @NotNull
        @JsonProperty("delivery_city")
        String deliveryCity,
        @NotNull
        @JsonProperty("delivery_state")
        String deliveryState,
        @NotNull
        @JsonProperty("delivery_zip")
        String deliveryZip) {
}
