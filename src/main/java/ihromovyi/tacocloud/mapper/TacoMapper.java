package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import ihromovyi.tacocloud.model.Taco;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = IngredientMapper.class)
public interface TacoMapper {
    @Mapping (target = "ingredientIds", source = "ingredients",
            qualifiedByName = "ingredientsToIds")
    TacoResponseDto toDto(Taco taco);

    @Mapping (target = "ingredients", source = "ingredientIds",
            qualifiedByName = "idsToIngredients")
    Taco toEntity(TacoRequestDto dto);

    @Named("idsToTacos")
    default Set<Taco> idsToTacos(Set<Long> ids) {
        return ids.stream()
                .map(Taco::new)
                .collect(Collectors.toSet());
    }

    @Named("tacosToIds")
    default Set<Long> tacosToIds(Set<Taco> tacos) {
        return tacos.stream()
                .map(Taco::getId)
                .collect(Collectors.toSet());
    }
}
