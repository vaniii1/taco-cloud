package ihromovyi.tacocloud.dto.taco;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record TacoResponseDto(
        Long id,
        String name,
        @JsonProperty("created_at")
        Date createdAt,
        BigDecimal price,
        @JsonProperty("ingredient_ids")
        List<Long> ingredientIds) {
}
