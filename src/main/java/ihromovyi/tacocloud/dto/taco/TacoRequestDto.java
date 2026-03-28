package ihromovyi.tacocloud.dto.taco;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record TacoRequestDto(
        @NotNull
        String name,
        @NotEmpty
        Set<Long> ingredientIds) {
}
