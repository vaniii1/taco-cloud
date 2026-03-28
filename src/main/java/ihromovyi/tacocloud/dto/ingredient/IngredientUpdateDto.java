package ihromovyi.tacocloud.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IngredientUpdateDto(
        String name,
        String type,
        @JsonProperty("is_deleted")
        Boolean isDeleted
) {
}
