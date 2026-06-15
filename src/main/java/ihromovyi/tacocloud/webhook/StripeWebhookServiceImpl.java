package ihromovyi.tacocloud.webhook;

import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import ihromovyi.tacocloud.exception.PaymentNotFoundException;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.Payment;
import ihromovyi.tacocloud.repository.OrderRepository;
import ihromovyi.tacocloud.repository.PaymentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class StripeWebhookServiceImpl implements StripeWebhookService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    @Override
    @Transactional
    public void handleEvent(String payload, String signature)
            throws EventDataObjectDeserializationException {
        Event event;

        try {
            event = Webhook.constructEvent(
                    payload,
                    signature,
                    stripeWebhookSecret
            );
        } catch (SignatureVerificationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Stripe signature");
        }

        switch (event.getType()) {
            case "checkout.session.completed" -> handleSessionCompleted(event);
            case "checkout.session.expired" -> handleSessionExpired(event);
            default -> { }
        }
    }

    private void handleSessionExpired(Event event)
            throws EventDataObjectDeserializationException {
        Session session = (Session) event.getDataObjectDeserializer()
                .deserializeUnsafe();

        Payment payment = paymentRepository.findByStripeSessionId(session.getId()).orElseThrow(
                () -> new PaymentNotFoundException(
                        "Payment not found, session id: " + session.getId()));

        payment.setStatus(Payment.Status.FAILED);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        order.setStatus(Order.Status.CANCELED);
        order.setStatusChangedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    private void handleSessionCompleted(Event event)
            throws EventDataObjectDeserializationException {
        Session session = (Session) event.getDataObjectDeserializer()
                .deserializeUnsafe();

        Payment payment = paymentRepository.findByStripeSessionId(session.getId()).orElseThrow(
                () -> new PaymentNotFoundException(
                        "Payment not found, session id: " + session.getId()));

        payment.setStatus(Payment.Status.COMPLETED);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        order.setStatus(Order.Status.PREPARING);
        order.setStatusChangedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}
