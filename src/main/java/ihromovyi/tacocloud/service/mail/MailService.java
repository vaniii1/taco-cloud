package ihromovyi.tacocloud.service.mail;

import ihromovyi.tacocloud.dto.order.OrderResponseDto;

public interface MailService {
    void sendOrderConfirmationMail(String toMail, OrderResponseDto order);

    void sendOrderUpdateMail(String toMail, OrderResponseDto order);
}
