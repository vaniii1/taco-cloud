package ihromovyi.tacocloud.dto.event;

import ihromovyi.tacocloud.dto.order.OrderResponseDto;

public record OrderStatusChangedEvent(
        String toMail,
        OrderResponseDto order) {
}
