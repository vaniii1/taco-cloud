package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class,
        uses = OrderItemMapper.class)
public interface OrderMapper {
    Order toEntity(OrderRequestDto dto);

    OrderResponseDto toDto(Order order);
}
