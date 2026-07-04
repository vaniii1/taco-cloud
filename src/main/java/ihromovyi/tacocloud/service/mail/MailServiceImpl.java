package ihromovyi.tacocloud.service.mail;

import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    @Value("${app.mail}")
    private String fromMail;

    @Override
    public void sendOrderConfirmationMail(String toMail, OrderResponseDto order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toMail);
        message.setSubject("Order Confirmation - " + order.id());
        message.setText("Thanks for your order! Your order ID is " + order.id()
                + ".\n" + buildOrderDetailsText(order));
        mailSender.send(message);
    }

    @Override
    public void sendOrderUpdateMail(String toMail, OrderResponseDto order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toMail);
        message.setSubject("Order Status Update - " + order.id());
        message.setText("You Order status was updated to " + order.status()
                + ".\n" + buildOrderDetailsText(order));
        mailSender.send(message);
    }

    private String buildOrderDetailsText(OrderResponseDto order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(order.id()).append("\n");
        sb.append("Status: ").append(order.status()).append("\n\n");
        sb.append("Delivery Address:\n");
        sb.append(order.deliveryName()).append("\n");
        sb.append(order.deliveryStreet()).append("\n");
        sb.append(order.deliveryCity()).append(", ").append(order.deliveryState())
                .append(" ").append(order.deliveryZip()).append("\n\n");
        sb.append("Items:\n");
        order.items().forEach(item ->
                sb.append(String.format("  %dx %-25s $%.2f%n",
                        item.quantity(), item.tacoName(), item.subtotal())));
        sb.append("\nTotal: $").append(order.totalPrice());
        return sb.toString();
    }

}
