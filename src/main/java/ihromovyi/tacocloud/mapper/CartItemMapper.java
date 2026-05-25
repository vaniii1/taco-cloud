package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.cart.CartItemResponseDto;
import ihromovyi.tacocloud.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "tacoId", source = "taco.id")
    @Mapping(target = "tacoName", source = "taco.name")
    CartItemResponseDto toResponse(CartItem cartItem);
}
