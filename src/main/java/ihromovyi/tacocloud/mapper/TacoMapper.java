package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import ihromovyi.tacocloud.dto.taco.TacoUpdateDto;
import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.model.Taco;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface TacoMapper {
    @Mapping (target = "ingredientIds", source = "ingredients",
            qualifiedByName = "ingredientsToIds")
    @Mapping(target = "price", expression = "java(taco.getPrice())")
    TacoResponseDto toDto(Taco taco);

    @Mapping (target = "ingredients", source = "ingredientIds",
            qualifiedByName = "idsToIngredients")
    Taco toEntity(TacoRequestDto dto);

    @Mapping (target = "ingredients", source = "ingredientIds",
            qualifiedByName = "idsToIngredients")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Taco update(@MappingTarget Taco taco, TacoUpdateDto dto);

    @Named("idsToIngredients")
    default List<Ingredient> idsToIngredients(List<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream()
                .map(Ingredient::new)
                .toList();
    }

    @Named("ingredientsToIds")
    default List<Long> ingredientsToIds(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getId)
                .toList();
    }
}
