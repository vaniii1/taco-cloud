package ihromovyi.tacocloud.dto.order;

import ihromovyi.tacocloud.model.Order;

public record OrderStatusDto(Order.Status status) {
}
