package ihromovyi.tacocloud.service.ingredient;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientUpdateDto;
import ihromovyi.tacocloud.model.Ingredient;
import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public interface IngredientService {
    IngredientResponseDto save(@Valid IngredientRequestDto dto);

    IngredientResponseDto getById(Long id);

    Set<IngredientResponseDto> getAll();

    Set<IngredientResponseDto> getAllByType(Ingredient.Type type);

    IngredientResponseDto updateById(Long id, @Valid IngredientUpdateDto ingredient);

    void deleteById(Long id);
}
