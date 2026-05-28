package ihromovyi.tacocloud.client;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyStripeClient {
    private static final String CURRENCY_CODE = "usd";

    @Value("${stripe.apiKey}")
    private String stripeApiKey;
    private StripeClient client;

    @PostConstruct
    public void init() {
        client = new StripeClient(stripeApiKey);
    }

    public Customer createCustomer(String firstName,
                                   String lastName,
                                   String email,
                                   String country,
                                   String city
    ) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setName(firstName + " " + lastName)
                .setEmail(email)
                .setAddress(
                        CustomerCreateParams.Address.builder()
                                .setCountry(country == null ? "" : country)
                                .setCity(city == null ? "" : city)
                                .build()
                )
                .build();
        return client.v1().customers().create(params);
    }

    public PaymentIntent createPaymentIntent(BigDecimal amount,
                                             String customerStripeId
    ) throws StripeException {
        long amountInCents = amount.multiply(BigDecimal.valueOf(100)).longValue();

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amountInCents)
                        .setCurrency(CURRENCY_CODE)
                        .setCustomer(customerStripeId)
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();
        return client.v1().paymentIntents().create(params);
    }

    public void cancelPaymentIntent(String stripePaymentIntentId) throws StripeException {
        client.v1().paymentIntents().cancel(stripePaymentIntentId);
    }

    public void confirmPaymentIntent(String stripePaymentIntentId) throws StripeException {
        client.v1().paymentIntents().confirm(stripePaymentIntentId);
    }
}
