package ihromovyi.tacocloud.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemRequestDto(
        @NotNull
        @JsonProperty("taco_id")
        Long tacoId,
        @NotNull
        @Min(1)
        Integer quantity
) {
}
