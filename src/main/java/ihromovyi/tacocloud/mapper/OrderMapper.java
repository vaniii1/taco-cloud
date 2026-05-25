package ihromovyi.tacocloud.mapper;

import ihromovyi.tacocloud.config.MapperConfig;
import ihromovyi.tacocloud.dto.order.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.order.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.order.TacoOrderUpdateDto;
import ihromovyi.tacocloud.model.CartItem;
import ihromovyi.tacocloud.model.Order;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    TacoOrderResponseDto toDto(Order order);

    Order toEntity(TacoOrderRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order update(@MappingTarget Order order, TacoOrderUpdateDto dto);

    @Named("idsToItems")
    default Set<CartItem> idsToItems(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream()
                .map(CartItem::new)
                .collect(Collectors.toSet());
    }

    @Named("itemsToIds")
    default Set<Long> itemsToIds(Set<CartItem> items) {
        return items.stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet());
    }
}
