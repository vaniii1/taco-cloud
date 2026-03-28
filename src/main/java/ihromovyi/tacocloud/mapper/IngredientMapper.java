package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientUpdateDto;
import ihromovyi.tacocloud.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface IngredientMapper {
    IngredientResponseDto toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientRequestDto ingredientRequestDto);

    Ingredient update(@MappingTarget Ingredient ingredient,
                      IngredientUpdateDto ingredientUpdateDto);
}
