package ihromovyi.tacocloud.service.order;

import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.dto.order.OrderStatusDto;
import ihromovyi.tacocloud.model.Order;
import jakarta.validation.Valid;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(@Valid OrderRequestDto dto);

    OrderResponseDto getLastOrder();

    List<OrderResponseDto> getAllByStatus(Order.Status status);

    List<OrderResponseDto> getAll();

    List<OrderResponseDto> getAllOrdersByUserId(Long userId);

    OrderResponseDto updateOrderStatusByOrderId(OrderStatusDto statusDto, Long orderId);
}
