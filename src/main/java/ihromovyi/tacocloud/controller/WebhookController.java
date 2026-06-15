package ihromovyi.tacocloud.controller;

import com.stripe.exception.EventDataObjectDeserializationException;
import ihromovyi.tacocloud.webhook.StripeWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class WebhookController {
    private final StripeWebhookService stripeWebhookService;

    @PostMapping("/stripe")
    public ResponseEntity<String> stripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature)
            throws EventDataObjectDeserializationException {
        stripeWebhookService.handleEvent(payload, signature);
        return ResponseEntity.ok("Success");
    }
}
