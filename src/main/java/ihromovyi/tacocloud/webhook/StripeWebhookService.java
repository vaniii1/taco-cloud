package ihromovyi.tacocloud.webhook;

import com.stripe.exception.EventDataObjectDeserializationException;

public interface StripeWebhookService {
    void handleEvent(String payload, String signature)
            throws EventDataObjectDeserializationException;
}
