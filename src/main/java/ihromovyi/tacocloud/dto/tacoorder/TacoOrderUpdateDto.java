package ihromovyi.tacocloud.dto.tacoorder;

import java.util.Set;

public record TacoOrderUpdateDto(
        String deliveryName,
        String deliveryStreet,
        String deliveryCity,
        String deliveryState,
        String deliveryZip,
        String ccNumber,
        String ccExpiration,
        String ccCvv,
        Set<Long> tacoIds
) {
}
