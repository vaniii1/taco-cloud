package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.tacoorder.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderUpdateDto;
import ihromovyi.tacocloud.service.tacoorder.TacoOrderService;
import jakarta.validation.Valid;
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
@RequestMapping("/taco_order")
public class TacoOrderController {
    private final TacoOrderService tacoOrderService;

    @PostMapping
    public TacoOrderResponseDto saveTaco(
            @RequestBody @Valid TacoOrderRequestDto dto) {
        return tacoOrderService.save(dto);
    }

    @GetMapping("/{id}")
    public TacoOrderResponseDto getTacoById(@PathVariable Long id) {
        return tacoOrderService.getById(id);
    }

    @GetMapping
    public Set<TacoOrderResponseDto> getAllTacos() {
        return tacoOrderService.getAll();
    }

    @PatchMapping("/{id}")
    public TacoOrderResponseDto updateTacoById(
            @PathVariable Long id,
            @RequestBody @Valid TacoOrderUpdateDto dto) {
        return tacoOrderService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTacoById(@PathVariable Long id) {
        tacoOrderService.deleteById(id);
    }
}
