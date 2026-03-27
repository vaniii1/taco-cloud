package ihromovyi.tacocloud.dto;

import ihromovyi.tacocloud.model.Ingredient;

public record IngredientDto(
        String name,
        Ingredient.Type type) {
}
