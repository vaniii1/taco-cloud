package ihromovyi.tacocloud.service.payment;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.payment.PaymentResponseDto;
import ihromovyi.tacocloud.dto.payment.PaymentSession;
import ihromovyi.tacocloud.dto.payment.PaymentStatusDto;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.User;
import java.util.List;

public interface PaymentService {
    PaymentSession createCheckoutSessionForOrder(Order order, User user) throws StripeException;

    PaymentResponseDto getPaymentByOrderId(Long orderId);

    PaymentResponseDto getLast();

    List<PaymentResponseDto> getMyPayments();

    List<PaymentResponseDto> getPaymentsByUserId(Long userId);

    List<PaymentResponseDto> getMyPaymentsByStatus(PaymentStatusDto status);

    List<PaymentResponseDto> getPaymentsByUserIdAndStatus(Long userId, PaymentStatusDto status);
}
