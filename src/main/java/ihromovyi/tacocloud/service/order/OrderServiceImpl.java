package ihromovyi.tacocloud.service.order;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.order.CheckoutResponse;
import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.payment.PaymentSession;
import ihromovyi.tacocloud.exception.CartNotFoundException;
import ihromovyi.tacocloud.exception.EmptyCartException;
import ihromovyi.tacocloud.mapper.OrderItemMapper;
import ihromovyi.tacocloud.mapper.OrderMapper;
import ihromovyi.tacocloud.model.Cart;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.CartRepository;
import ihromovyi.tacocloud.repository.OrderRepository;
import ihromovyi.tacocloud.service.payment.PaymentService;
import ihromovyi.tacocloud.service.user.UserService;
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
}
