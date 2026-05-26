package ihromovyi.tacocloud.service.order;

import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.dto.order.OrderStatusDto;
import jakarta.validation.Valid;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(@Valid OrderRequestDto dto);

    OrderResponseDto getLastOrder();

    List<OrderResponseDto> getAllByStatus(OrderStatusDto status);

    List<OrderResponseDto> getAll();

    List<OrderResponseDto> getAllOrdersByUserId(Long userId);

    OrderResponseDto updateOrderStatusByOrderId(OrderStatusDto statusDto, Long orderId);
}
