package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.model.Ingredient;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface IngredientMapper {
    IngredientResponseDto toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientRequestDto ingredientRequestDto);

    @Named("idsToIngredients")
    default Set<Ingredient> idsToIngredients(Set<Long> ids) {
        return ids.stream()
                .map(Ingredient::new)
                .collect(Collectors.toSet());
    }

    @Named("ingredientsToIds")
    default Set<Long> ingredientsToIds(Set<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getId)
                .collect(Collectors.toSet());
    }
}
