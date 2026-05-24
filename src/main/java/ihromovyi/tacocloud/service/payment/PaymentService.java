package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.model.Payment;
import java.math.BigDecimal;

public interface PaymentService {
    Payment createPayment(BigDecimal amount, String currency, Long userId) throws StripeException;

    Payment declinePayment(Long paymentId) throws StripeException;

    Payment confirmPayment(Long paymentId) throws StripeException;
}
