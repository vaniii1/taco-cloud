package ihromovyi.tacocloud.service.ingredient;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientUpdateDto;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public interface IngredientService {
    IngredientResponseDto save(IngredientRequestDto dto);

    IngredientResponseDto getById(Long id);

    Set<IngredientResponseDto> getAll();

    IngredientResponseDto updateById(Long id, IngredientUpdateDto ingredient);

    void deleteById(Long id);
}
