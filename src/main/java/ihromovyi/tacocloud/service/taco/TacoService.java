package ihromovyi.tacocloud.service.taco;

import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import ihromovyi.tacocloud.dto.taco.TacoUpdateDto;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public interface TacoService {
    TacoResponseDto save(TacoRequestDto dto);

    TacoResponseDto getById(Long id);

    Set<TacoResponseDto> getAll();

    TacoResponseDto updateById(Long id, TacoUpdateDto dto);

    void deleteById(Long id);
}
