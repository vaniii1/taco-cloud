package ihromovyi.tacocloud.dto.taco;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TacoUpdateDto(
        String name,
        @JsonProperty("ingredient_ids")
        List<Long> ingredientIds,
        @JsonProperty("is_deleted")
        Boolean isDeleted) {
}
