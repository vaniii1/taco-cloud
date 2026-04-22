package ihromovyi.tacocloud.dto.taco;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public record TacoUpdateDto(
        String name,
        @JsonProperty("ingredient_ids")
        Set<Long> ingredientIds,
        @JsonProperty("is_deleted")
        Boolean isDeleted) {
}
