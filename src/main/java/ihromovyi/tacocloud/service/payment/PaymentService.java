package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.payment.PaymentSession;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.User;

public interface PaymentService {
    PaymentSession createCheckoutSessionForOrder(Order order, User user) throws StripeException;

}
