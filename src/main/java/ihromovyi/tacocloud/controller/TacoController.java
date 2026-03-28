package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import ihromovyi.tacocloud.dto.taco.TacoUpdateDto;
import ihromovyi.tacocloud.service.taco.TacoService;
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
@RequestMapping("/taco")
public class TacoController {
    private final TacoService tacoService;

    @PostMapping
    public TacoResponseDto saveTaco(@RequestBody TacoRequestDto dto) {
        return tacoService.save(dto);
    }

    @GetMapping("/{id}")
    public TacoResponseDto getTacoById(@PathVariable Long id) {
        return tacoService.getById(id);
    }

    @GetMapping
    public Set<TacoResponseDto> getAllTacos() {
        return tacoService.getAll();
    }

    @PatchMapping("/{id}")
    public TacoResponseDto updateTacoById(
            @PathVariable Long id,
            @RequestBody TacoUpdateDto dto) {
        return tacoService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTacoById(@PathVariable Long id) {
        tacoService.deleteById(id);
    }
}
