package ihromovyi.tacocloud.dto.taco;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public record TacoResponseDto(
        Long id,
        String name,
        @JsonProperty("ingredient_ids")
        Set<Long> ingredientIds) {
}
