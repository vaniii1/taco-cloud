package ihromovyi.tacocloud.webhook;

public interface StripeWebhookService {

    void handleEvent(String payload, String signature);
}
