package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import ihromovyi.tacocloud.client.MyStripeClient;
import ihromovyi.tacocloud.dto.payment.PaymentResponseDto;
import ihromovyi.tacocloud.dto.payment.PaymentSession;
import ihromovyi.tacocloud.dto.payment.PaymentStatusDto;
import ihromovyi.tacocloud.exception.PaymentNotFoundException;
import ihromovyi.tacocloud.mapper.PaymentMapper;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.Payment;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.PaymentRepository;
import ihromovyi.tacocloud.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final MyStripeClient stripeClient;
    private final UserService userService;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found with orderId: " + orderId));
        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDto getLast() {
        Long userId = userService.getCurrentUser().getId();
        Payment lastPayment = paymentRepository.findLastPaymentByUserId(userId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found. User_id: " + userId));
        return paymentMapper.toDto(lastPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getMyPayments() {
        Long userId = userService.getCurrentUser().getId();
        return paymentRepository.findAllByUserId(userId)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByUserId(Long userId) {
        return paymentRepository.findAllByUserId(userId)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getMyPaymentsByStatus(PaymentStatusDto status) {
        Long userId = userService.getCurrentUser().getId();
        return paymentRepository.findAllByUserIdAndStatus(
                    userId, status.toStatus())
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByUserIdAndStatus(
            Long userId,
            PaymentStatusDto status
    ) {
        return paymentRepository.findAllByUserIdAndStatus(
                    userId, status.toStatus())
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }
}
