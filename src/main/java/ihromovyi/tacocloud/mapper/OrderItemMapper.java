package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.order.OrderItemResponseDto;
import ihromovyi.tacocloud.model.CartItem;
import ihromovyi.tacocloud.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subtotal", expression = "java(cartItem.getSubtotal())")
    OrderItem toOrderItem(CartItem cartItem);

    @Mapping(target = "tacoId", source = "taco.id")
    @Mapping(target = "tacoName", source = "taco.name")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
