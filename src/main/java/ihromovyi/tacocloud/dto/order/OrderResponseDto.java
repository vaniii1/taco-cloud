package ihromovyi.tacocloud.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import ihromovyi.tacocloud.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long id,
        @JsonProperty("delivery_name")
        String deliveryName,
        @JsonProperty("delivery_street")
        String deliveryStreet,
        @JsonProperty("delivery_city")
        String deliveryCity,
        @JsonProperty("delivery_state")
        String deliveryState,
        @JsonProperty("delivery_zip")
        String deliveryZip,
        @JsonProperty("placed_at")
        LocalDateTime placedAt,
        @JsonProperty("delivered_at")
        LocalDateTime deliveredAt,
        @JsonProperty("status_changed_at")
        LocalDateTime statusChangedAt,
        Order.Status status,
        @JsonProperty("items")
        Set<OrderItemResponseDto> items,
        @JsonProperty("total_price")
        BigDecimal totalPrice) {
}
