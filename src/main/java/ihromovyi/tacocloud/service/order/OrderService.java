package ihromovyi.tacocloud.service.order;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.order.CheckoutResponse;
import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.dto.order.OrderStatusDto;
import jakarta.validation.Valid;
import java.util.List;

public interface OrderService {
    CheckoutResponse createOrder(@Valid OrderRequestDto dto) throws StripeException;

    OrderResponseDto getLast();

    List<OrderResponseDto> getMyOrders();

    List<OrderResponseDto> getMyOrdersByStatus(OrderStatusDto status);

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    List<OrderResponseDto> getOrdersByUserIdAndStatus(Long userId, OrderStatusDto status);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatusDto status);

    OrderResponseDto getOrderById(Long orderId);
}
