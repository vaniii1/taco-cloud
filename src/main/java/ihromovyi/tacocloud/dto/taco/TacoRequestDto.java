package ihromovyi.tacocloud.dto.taco;

import java.util.Set;

public record TacoRequestDto(
        String name,
        Set<Long> ingredientIds) {
}
