package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.cart.CartResponseDto;
import ihromovyi.tacocloud.model.Cart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class,
        uses = CartItemMapper.class)
public interface CartMapper {

    CartResponseDto toDto(Cart cart);
}
