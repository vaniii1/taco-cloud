package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.payment.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createPaymentForLatestOrder() throws StripeException;

    PaymentResponseDto declinePayment(Long paymentId) throws StripeException;

    PaymentResponseDto confirmPayment(Long paymentId) throws StripeException;
}
