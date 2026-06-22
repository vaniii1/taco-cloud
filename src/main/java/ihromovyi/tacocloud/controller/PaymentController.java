package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.payment.PaymentResponseDto;
import ihromovyi.tacocloud.dto.payment.PaymentStatusDto;
import ihromovyi.tacocloud.service.payment.PaymentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/last")
    public PaymentResponseDto getLastPayment() {
        return paymentService.getLast();
    }

    @GetMapping
    public List<PaymentResponseDto> getMyPaymentsByStatus(
            @RequestParam(required = false) @Valid PaymentStatusDto status) {
        return status == null
                ? paymentService.getMyPayments()
                : paymentService.getMyPaymentsByStatus(status);
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    public PaymentResponseDto getPaymentByOrderId(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    public List<PaymentResponseDto> getPaymentsByUserIdAndStatus(
            @PathVariable Long userId,
            @RequestParam(required = false) @Valid PaymentStatusDto status
    ) {
        return status == null
                ? paymentService.getPaymentsByUserId(userId)
                : paymentService.getPaymentsByUserIdAndStatus(userId, status);
    }
}
