package ihromovyi.tacocloud.dto.taco;

import java.util.Set;

public record TacoResponseDto(
        Long id,
        String name,
        Set<Long> ingredientIds) {
}
