package ihromovyi.tacocloud.dto.event;

import ihromovyi.tacocloud.dto.order.OrderResponseDto;

public record OrderCreatedEvent(
        String toMail,
        OrderResponseDto order) {
}
