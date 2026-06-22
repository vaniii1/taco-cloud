package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.payment.PaymentResponseDto;
import ihromovyi.tacocloud.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentRedirectionController {
    private final PaymentService paymentService;

    @GetMapping("/success")
    public String successPayment(@RequestParam Long orderId, Model model) {
        PaymentResponseDto payment = paymentService.getPaymentByOrderId(orderId);
        model.addAttribute("payment", payment);
        return "payment-success";
    }

    @GetMapping("/cancel")
    public String cancelPayment(@RequestParam Long orderId, Model model) {
        PaymentResponseDto payment = paymentService.getPaymentByOrderId(orderId);
        model.addAttribute("payment", payment);
        return "payment-cancel";
    }

}
