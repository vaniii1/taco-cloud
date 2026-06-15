package ihromovyi.tacocloud.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckoutResponse(
        OrderResponseDto order,
        @JsonProperty("checkout_url")
        String checkoutUrl
) {}
