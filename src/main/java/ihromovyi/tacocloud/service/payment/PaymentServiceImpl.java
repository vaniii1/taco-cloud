package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import ihromovyi.tacocloud.client.MyStripeClient;
import ihromovyi.tacocloud.exception.PaymentNotFoundException;
import ihromovyi.tacocloud.exception.UserNotFoundException;
import ihromovyi.tacocloud.model.Payment;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.PaymentRepository;
import ihromovyi.tacocloud.repository.UserRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final MyStripeClient stripeClient;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public Payment createPayment(BigDecimal amount, Long userId)
            throws StripeException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        User user = optionalUser.get();
        setCustomerIdToUser(user);
        PaymentIntent paymentIntent = stripeClient.createPaymentIntent(
                amount, user.getStripeCustomerId());
        Payment payment = new Payment();
        payment.setUser(new User(user.getId()));
        payment.setAmount(amount);
        payment.setStripePaymentIntentId(paymentIntent.getId());
        payment.setStatus(Payment.Status.PENDING);
        payment.setCreatedAt(new Date());
        paymentRepository.save(payment);
        return payment;
    }

    private void setCustomerIdToUser(User user) throws StripeException {
        if (user.getStripeCustomerId() == null) {
            Customer customer = stripeClient.createCustomer(user.getId(), user.getFirstName(),
                    user.getLastName(), user.getEmail(),
                    user.getCountry(), user.getCity());
            user.setStripeCustomerId(customer.getId());
            userRepository.save(user);
        }
    }

    @Override
    public Payment declinePayment(Long paymentId) throws StripeException {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isEmpty()) {
            throw new PaymentNotFoundException("Payment not found with id: " + paymentId);
        }
        Payment payment = optionalPayment.get();
        stripeClient.cancelPaymentIntent(payment.getStripePaymentIntentId());
        payment.setStatus(Payment.Status.DECLINED);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment confirmPayment(Long paymentId) throws StripeException {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isEmpty()) {
            throw new PaymentNotFoundException("Payment not found with id: " + paymentId);
        }
        Payment payment = optionalPayment.get();
        stripeClient.confirmPaymentIntent(payment.getStripePaymentIntentId());
        payment.setStatus(Payment.Status.CONFIRMED);
        paymentRepository.save(payment);
        return payment;
    }
}
