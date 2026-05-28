package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import ihromovyi.tacocloud.client.MyStripeClient;
import ihromovyi.tacocloud.dto.payment.PaymentResponseDto;
import ihromovyi.tacocloud.exception.OrderNotFoundException;
import ihromovyi.tacocloud.exception.PaymentNotFoundException;
import ihromovyi.tacocloud.mapper.PaymentMapper;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.Payment;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.OrderRepository;
import ihromovyi.tacocloud.repository.PaymentRepository;
import ihromovyi.tacocloud.service.user.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final MyStripeClient stripeClient;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponseDto createPaymentForLatestOrder()
            throws StripeException {

        User currentUser = userService.getCurrentUser();
        Long currentUserId = currentUser.getId();

        Order order = orderRepository.findLastOrderByUserId(currentUserId).orElseThrow(
                () -> new OrderNotFoundException("Order not found with userId: " + currentUserId));

        if (order.getStatus() != Order.Status.AWAITING_PAYMENT) {
            throw new IllegalStateException(
                    "Order is already " + order.getStatus() + ", cannot create payment");
        }

        PaymentIntent paymentIntent = stripeClient.createPaymentIntent(
                order.getTotalPrice(), currentUser.getStripeCustomerId());

        Payment payment = new Payment(currentUser, order, currentUser.getStripeCustomerId(),
                paymentIntent.getId(), order.getTotalPrice());
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDto declinePayment(Long paymentId) throws StripeException {
        Payment payment = getPaymentById(paymentId);
        stripeClient.cancelPaymentIntent(payment.getStripePaymentIntentId());
        payment.setStatus(Payment.Status.DECLINED);
        payment.setLastModifiedAt(LocalDateTime.now());
        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDto confirmPayment(Long paymentId) throws StripeException {
        Payment payment = getPaymentById(paymentId);
        stripeClient.confirmPaymentIntent(payment.getStripePaymentIntentId());
        payment.setStatus(Payment.Status.CONFIRMED);
        payment.setLastModifiedAt(LocalDateTime.now());
        Order order = payment.getOrder();
        updateOrderStatus(order, Order.Status.PREPARING);
        return paymentMapper.toDto(payment);
    }

    private Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
    }

    private void updateOrderStatus(Order order, Order.Status status) {
        order.setStatus(status);
        order.setStatusChangedAt(LocalDateTime.now());
    }
}
