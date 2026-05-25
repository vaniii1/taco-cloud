package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.order.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.order.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.order.TacoOrderUpdateDto;
import ihromovyi.tacocloud.service.order.OrderService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public TacoOrderResponseDto saveTaco(
            @RequestBody TacoOrderRequestDto dto) {
        return orderService.save(dto);
    }

    @GetMapping("/{id}")
    public TacoOrderResponseDto getTacoById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    public Set<TacoOrderResponseDto> getAllTacos() {
        return orderService.getAll();
    }

    @PatchMapping("/{id}")
    public TacoOrderResponseDto updateTacoById(
            @PathVariable Long id,
            @RequestBody TacoOrderUpdateDto dto) {
        return orderService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTacoById(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
