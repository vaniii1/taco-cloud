package ihromovyi.tacocloud.service.tacoorder;

import ihromovyi.tacocloud.dto.tacoorder.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderUpdateDto;
import java.util.Set;

public interface TacoOrderService {
    TacoOrderResponseDto save(TacoOrderRequestDto dto);

    TacoOrderResponseDto getById(Long id);

    Set<TacoOrderResponseDto> getAll();

    TacoOrderResponseDto updateById(Long id, TacoOrderUpdateDto dto);

    void deleteById(Long id);
}
