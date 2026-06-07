package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.order.OrderRequestDto;
import ihromovyi.tacocloud.dto.order.OrderResponseDto;
import ihromovyi.tacocloud.dto.order.OrderStatusDto;
import ihromovyi.tacocloud.model.Order;
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
    public OrderResponseDto placeOrder(
            @RequestBody OrderRequestDto dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/last")
    public OrderResponseDto getLastOrder() {
        return orderService.getLastOrder();
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrderByStatus(
            @RequestParam String status) {
        return orderService.getAllByStatus(
                Order.Status.valueOf(status.toUpperCase()));
    }

    @GetMapping("/all")
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    public List<OrderResponseDto> getAllOrdersByUserId(
            @PathVariable Long userId) {
        return orderService.getAllOrdersByUserId(userId);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    public OrderResponseDto updateOrderStatusById(
            @RequestBody @Valid OrderStatusDto status, @PathVariable Long id) {
        return orderService.updateOrderStatusByOrderId(status, id);
    }
}
