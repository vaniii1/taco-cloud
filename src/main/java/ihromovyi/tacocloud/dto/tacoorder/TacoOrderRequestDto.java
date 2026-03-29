package ihromovyi.tacocloud.dto.tacoorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import ihromovyi.tacocloud.validation.ValidExpiration;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import org.hibernate.validator.constraints.CreditCardNumber;

public record TacoOrderRequestDto(
        @NotNull
        @JsonProperty("delivery_name")
        String deliveryName,
        @NotNull
        @JsonProperty("delivery_street")
        String deliveryStreet,
        @NotNull
        @JsonProperty("delivery_city")
        String deliveryCity,
        @NotNull
        @JsonProperty("delivery_state")
        String deliveryState,
        @NotNull
        @JsonProperty("delivery_zip")
        String deliveryZip,
        @NotNull
        @CreditCardNumber
        @JsonProperty("cc_number")
        String ccNumber,
        @NotNull
        @ValidExpiration
        @JsonProperty("cc_expiration")
        String ccExpiration,
        @NotNull
        @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
        @JsonProperty("cc_cvv")
        String ccCvv,
        @NotEmpty
        @JsonProperty("taco_ids")
        Set<Long> tacoIds) {
}
