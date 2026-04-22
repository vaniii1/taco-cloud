package ihromovyi.tacocloud.service.taco;

import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import ihromovyi.tacocloud.dto.taco.TacoUpdateDto;
import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public interface TacoService {
    TacoResponseDto save(@Valid TacoRequestDto dto);

    TacoResponseDto getById(Long id);

    Set<TacoResponseDto> getAll();

    TacoResponseDto updateById(Long id, @Valid TacoUpdateDto dto);

    void deleteById(Long id);
}
