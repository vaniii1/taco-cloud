package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import ihromovyi.tacocloud.client.MyStripeClient;
import ihromovyi.tacocloud.dto.payment.PaymentResponseDto;
import ihromovyi.tacocloud.dto.payment.PaymentSession;
import ihromovyi.tacocloud.exception.PaymentNotFoundException;
import ihromovyi.tacocloud.mapper.PaymentMapper;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.Payment;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final MyStripeClient stripeClient;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentSession createCheckoutSessionForOrder(Order order, User user)
            throws StripeException {

        Session session = stripeClient.createCheckoutSession(
                order.getTotalPrice(),
                order.getId(),
                user.getStripeCustomerId()
        );

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setStripeSessionId(session.getId());
        payment.setStatus(Payment.Status.PENDING);

        paymentRepository.save(payment);

        return new PaymentSession(session.getUrl());
    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findById(orderId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found with orderId: " + orderId));
        return paymentMapper.toDto(payment);
    }
}
