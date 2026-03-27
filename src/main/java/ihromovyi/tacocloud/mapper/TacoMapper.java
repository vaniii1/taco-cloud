package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.TacoOrderDto;
import ihromovyi.tacocloud.model.Taco;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TacoMapper {
    TacoOrderDto toDto(Taco taco);

    Taco toEntity(TacoOrderDto dto);
}
