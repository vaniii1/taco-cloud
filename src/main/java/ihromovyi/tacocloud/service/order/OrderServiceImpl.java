package ihromovyi.tacocloud.service.order;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.order.CheckoutResponse;
import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.dto.order.OrderStatusDto;
import ihromovyi.tacocloud.dto.payment.PaymentSession;
import ihromovyi.tacocloud.exception.CartNotFoundException;
import ihromovyi.tacocloud.exception.EmptyCartException;
import ihromovyi.tacocloud.exception.InvalidStatusException;
import ihromovyi.tacocloud.exception.OrderNotFoundException;
import ihromovyi.tacocloud.mapper.OrderItemMapper;
import ihromovyi.tacocloud.mapper.OrderMapper;
import ihromovyi.tacocloud.model.Cart;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.CartRepository;
import ihromovyi.tacocloud.repository.OrderRepository;
import ihromovyi.tacocloud.service.payment.PaymentService;
import ihromovyi.tacocloud.service.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public CheckoutResponse createOrder(OrderRequestDto dto) throws StripeException {

        User user = userService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cart is empty");
        }

        Order order = orderMapper.toEntity(dto);
        order.setUser(user);
        order.setStatus(Order.Status.AWAITING_PAYMENT);
        order.setTotalPrice(cart.getTotalPrice());

        cart.getItems().stream()
                .map(orderItemMapper::toOrderItem)
                .forEach(order::addItem);

        orderRepository.save(order);

        cart.getItems().clear();

        PaymentSession session = paymentService.createCheckoutSessionForOrder(order, user);

        return new CheckoutResponse(
                orderMapper.toDto(order),
                session.checkoutUrl()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getLast() {
        Long userId = userService.getCurrentUser().getId();
        Order lastOrder = orderRepository.findLastOrderByUserId(userId).orElseThrow(
                () -> new OrderNotFoundException("Order not found, user_id: " + userId));
        return orderMapper.toDto(lastOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getMyOrders() {
        Long userId = userService.getCurrentUser().getId();
        return orderRepository.findAllByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getMyOrdersByStatus(OrderStatusDto status) {
        Long userId = userService.getCurrentUser().getId();
        return orderRepository.findAllByUserIdAndStatus(userId, status.toStatus())
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUserIdAndStatus(Long userId, OrderStatusDto status) {
        return orderRepository.findAllByUserIdAndStatus(userId, status.toStatus())
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusDto status) {
        Order order = getOrderFromDb(orderId);
        Order.Status newStatus = status.toStatus();
        verifyStatus(order.getStatus(), newStatus);
        order.setStatus(newStatus);
        order.setStatusChangedAt(LocalDateTime.now());
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = getOrderFromDb(orderId);
        return orderMapper.toDto(order);
    }

    private Order getOrderFromDb(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order not found, order_id: " + orderId));
    }

    private void verifyStatus(Order.Status current, Order.Status next) {
        if (!current.canTransitionTo(next)) {
            throw new InvalidStatusException(
                    "Cannot transition from " + current + " to " + next
            );
        }
    }

}
