package ihromovyi.tacocloud.service.order;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.order.CheckoutResponse;
import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import jakarta.validation.Valid;

public interface OrderService {
    CheckoutResponse createOrder(@Valid OrderRequestDto dto) throws StripeException;
}
