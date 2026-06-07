package ihromovyi.tacocloud.service.order;

import static ihromovyi.tacocloud.model.Order.Status.CANCELED;
import static ihromovyi.tacocloud.model.Order.Status.DELIVERED;
import static ihromovyi.tacocloud.model.Order.Status.ON_THE_WAY;
import static ihromovyi.tacocloud.model.Order.Status.PREPARING;

import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.dto.order.OrderStatusDto;
import ihromovyi.tacocloud.exception.CartNotFoundException;
import ihromovyi.tacocloud.exception.EmptyCartException;
import ihromovyi.tacocloud.exception.InvalidStatusException;
import ihromovyi.tacocloud.exception.OrderNotFoundException;
import ihromovyi.tacocloud.mapper.OrderItemMapper;
import ihromovyi.tacocloud.mapper.OrderMapper;
import ihromovyi.tacocloud.model.Cart;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.OrderItem;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.CartRepository;
import ihromovyi.tacocloud.repository.OrderRepository;
import ihromovyi.tacocloud.service.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = orderMapper.toEntity(dto);

        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId()).orElseThrow(
                () -> new CartNotFoundException(
                        "Cart not found with userId: " + currentUser.getId()));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cannot create order from empty cart");
        }

        order.setUser(currentUser);
        order.setTotalPrice(cart.getTotalPrice());
        List<OrderItem> items = cart.getItems()
                .stream().map(orderItemMapper::toOrderItem).toList();
        for (OrderItem orderItem : items) {
            order.addItem(orderItem);
        }
        orderRepository.save(order);
        cart.getItems().clear();
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getLastOrder() {
        Long currentUserId = userService.getCurrentUser().getId();
        Order order = orderRepository.findLastOrderByUserId(currentUserId).orElseThrow(
                () -> new OrderNotFoundException("Order not found with userId: " + currentUserId));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllByStatus(Order.Status status) {
        User currentUser = userService.getCurrentUser();
        List<Order> orders = orderRepository
                .findAllByUserIdAndStatus(currentUser.getId(), status);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAll() {
        User currentUser = userService.getCurrentUser();
        List<Order> orders = orderRepository.findAllByUserId(currentUser.getId());
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatusByOrderId(
            OrderStatusDto statusDto, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order not found with id: " + orderId));
        Order.Status status = statusDto.toStatus();
        if (!isValidTransition(order.getStatus(),
                status)) {
            throw new InvalidStatusException(
                    "Cannot transition from "
                            + order.getStatus()
                            + " to "
                            + statusDto.status()
            );

        }
        if (status.equals(DELIVERED)) {
            order.setDeliveredAt(LocalDateTime.now());
        }
        order.setStatus(status);
        order.setStatusChangedAt(LocalDateTime.now());
        return orderMapper.toDto(order);
    }

    private boolean isValidTransition(Order.Status oldStatus,
                                     Order.Status newStatus) {
        return switch (oldStatus) {
            case AWAITING_PAYMENT ->
                    newStatus == PREPARING
                            || newStatus == CANCELED;

            case PREPARING ->
                    newStatus == ON_THE_WAY
                            || newStatus == CANCELED;

            case ON_THE_WAY ->
                    newStatus == DELIVERED;
            case CANCELED, DELIVERED -> false;
        };
    }
}
