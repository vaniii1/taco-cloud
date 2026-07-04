package ihromovyi.tacocloud.service.mail;

import ihromovyi.tacocloud.dto.event.OrderCreatedEvent;
import ihromovyi.tacocloud.dto.event.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderMailListener {
    private final MailService mailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendOrderConfirmationMail(OrderCreatedEvent event) {
        mailService.sendOrderConfirmationMail(event.toMail(), event.order());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendOrderUpdateMail(OrderStatusChangedEvent event) {
        mailService.sendOrderUpdateMail(event.toMail(), event.order());
    }
}
