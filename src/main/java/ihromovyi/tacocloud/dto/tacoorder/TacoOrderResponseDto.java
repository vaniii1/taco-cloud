package ihromovyi.tacocloud.dto.tacoorder;

import java.util.Set;

public record TacoOrderResponseDto(
        Long id,
        String deliveryName,
        String deliveryStreet,
        String deliveryCity,
        String deliveryState,
        String deliveryZip,
        Set<Long> tacoIds
) {
}
