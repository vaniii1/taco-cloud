package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.IngredientDto;
import ihromovyi.tacocloud.model.Ingredient;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface IngredientMapper {
    IngredientDto toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientDto ingredientDto);
}
