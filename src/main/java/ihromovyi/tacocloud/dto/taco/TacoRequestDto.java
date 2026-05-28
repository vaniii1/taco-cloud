package ihromovyi.tacocloud.dto.taco;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TacoRequestDto(
        @NotNull
        String name,
        @NotEmpty
        @JsonProperty("ingredient_ids")
        List<Long> ingredientIds) {
}
