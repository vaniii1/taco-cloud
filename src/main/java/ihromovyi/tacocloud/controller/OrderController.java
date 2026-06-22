package ihromovyi.tacocloud.controller;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.order.CheckoutResponse;
import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.dto.order.OrderStatusDto;
import ihromovyi.tacocloud.service.order.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckoutResponse placeOrder(
            @RequestBody @Valid OrderRequestDto dto) throws StripeException {
        return orderService.createOrder(dto);
    }

    @GetMapping("/last")
    public OrderResponseDto getLastOrder() {
        return orderService.getLast();
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    public OrderResponseDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/my")
    public List<OrderResponseDto> getMyOrders(
            @RequestParam(required = false) @Valid OrderStatusDto status) {
        return status == null
                ? orderService.getMyOrders()
                : orderService.getMyOrdersByStatus(status);
    }

    @PatchMapping("/update/{orderId}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    public OrderResponseDto updateOrderStatusById(
            @PathVariable Long orderId,
            @RequestParam @Valid OrderStatusDto status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    public List<OrderResponseDto> getOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false) @Valid OrderStatusDto status
    ) {
        return status == null
                ? orderService.getOrdersByUserId(userId)
                : orderService.getOrdersByUserIdAndStatus(userId, status);
    }
}
