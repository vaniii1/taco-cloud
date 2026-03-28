package ihromovyi.tacocloud.dto.ingredient;

import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(
        @NotNull
        String name,
        String type) {
}
