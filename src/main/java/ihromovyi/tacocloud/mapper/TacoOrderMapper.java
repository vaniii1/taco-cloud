package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderUpdateDto;
import ihromovyi.tacocloud.model.Taco;
import ihromovyi.tacocloud.model.TacoOrder;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface TacoOrderMapper {
    @Mapping(target = "tacoIds", source = "tacos",
            qualifiedByName = "tacosToIds")
    TacoOrderResponseDto toDto(TacoOrder order);

    @Mapping(target = "tacos", source = "tacoIds",
            qualifiedByName = "idsToTacos")
    TacoOrder toEntity(TacoOrderRequestDto dto);

    @Mapping(target = "tacos", source = "tacoIds",
            qualifiedByName = "idsToTacos")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TacoOrder update(@MappingTarget TacoOrder order, TacoOrderUpdateDto dto);

    @Named("idsToTacos")
    default Set<Taco> idsToTacos(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
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
