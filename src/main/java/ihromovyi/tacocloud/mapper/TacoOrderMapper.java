package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.TacoOrderDto;
import ihromovyi.tacocloud.model.TacoOrder;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TacoOrderMapper {
    TacoOrderDto toDto(TacoOrder taco);

    TacoOrder toEntity(TacoOrderDto dto);
}
