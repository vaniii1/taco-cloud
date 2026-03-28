package ihromovyi.tacocloud.dto.tacoorder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record TacoOrderRequestDto(
        @NotNull
        String deliveryName,
        @NotNull
        String deliveryStreet,
        @NotNull
        String deliveryCity,
        @NotNull
        String deliveryState,
        @NotNull
        String deliveryZip,
        @NotNull
        String ccNumber,
        @NotNull
        String ccExpiration,
        @NotNull
        String ccCvv,
        @NotEmpty
        Set<Long> tacoIds) {
}
