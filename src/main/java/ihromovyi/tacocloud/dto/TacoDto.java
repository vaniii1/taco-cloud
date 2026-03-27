package ihromovyi.tacocloud.dto;

import java.util.Set;

public record TacoDto(
        String name,
        Set<Long> ingredientIds) {
}
