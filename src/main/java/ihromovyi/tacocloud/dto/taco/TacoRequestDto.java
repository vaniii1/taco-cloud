package ihromovyi.tacocloud.dto.taco;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record TacoRequestDto(
        @NotNull
        String name,
        @NotEmpty
        @JsonProperty("ingredient_ids")
        Set<Long> ingredientIds) {
}
