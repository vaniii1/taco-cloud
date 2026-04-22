package ihromovyi.tacocloud.service.tacoorder;

import ihromovyi.tacocloud.dto.tacoorder.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderUpdateDto;
import jakarta.validation.Valid;
import java.util.Set;

public interface TacoOrderService {
    TacoOrderResponseDto save(@Valid TacoOrderRequestDto dto);

    TacoOrderResponseDto getById(Long id);

    Set<TacoOrderResponseDto> getAll();

    TacoOrderResponseDto updateById(Long id, @Valid TacoOrderUpdateDto dto);

    void deleteById(Long id);
}
