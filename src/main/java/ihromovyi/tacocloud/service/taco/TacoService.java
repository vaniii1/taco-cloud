package ihromovyi.tacocloud.service.taco;

import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public interface TacoService {
    TacoResponseDto save(TacoRequestDto requestDto);

    TacoResponseDto getById(Long id);

    Set<TacoResponseDto> getAll();
}
