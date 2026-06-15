package ihromovyi.tacocloud.client;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyStripeClient {
    private static final String CURRENCY_CODE = "usd";
    private static final String PAYMENT_URL = "http://localhost:8080/taco_cloud_api/payments/";

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

    public Session createCheckoutSession(
            BigDecimal amount,
            Long orderId,
            String customerId
    ) throws StripeException {

        long cents = amount.multiply(BigDecimal.valueOf(100)).longValue();

        SessionCreateParams params =
                SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCustomer(customerId)
                    .setSuccessUrl(PAYMENT_URL + "success?orderId=" + orderId)
                    .setCancelUrl(PAYMENT_URL + "cancel?orderId=" + orderId)
                    .addLineItem(
                        SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency(CURRENCY_CODE)
                                    .setUnitAmount(cents)
                                    .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Order #" + orderId)
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build();

        return client.v1().checkout().sessions().create(params);
    }
}
