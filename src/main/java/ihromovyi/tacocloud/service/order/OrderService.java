package ihromovyi.tacocloud.service.order;

import ihromovyi.tacocloud.dto.order.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.order.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.order.TacoOrderUpdateDto;
import jakarta.validation.Valid;
import java.util.Set;

public interface OrderService {
    TacoOrderResponseDto save(@Valid TacoOrderRequestDto dto);

    TacoOrderResponseDto getById(Long id);

    Set<TacoOrderResponseDto> getAll();

    TacoOrderResponseDto updateById(Long id, @Valid TacoOrderUpdateDto dto);

    void deleteById(Long id);
}
