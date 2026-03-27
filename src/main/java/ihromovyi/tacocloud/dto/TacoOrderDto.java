package ihromovyi.tacocloud.dto;

import java.util.Set;

public record TacoOrderDto(
        String deliveryName,
        String deliveryStreet,
        String deliveryCity,
        String deliveryState,
        String deliveryZip,
        Set<Long> tacoIds) {
}
