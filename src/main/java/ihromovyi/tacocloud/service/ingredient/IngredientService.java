package ihromovyi.tacocloud.service.ingredient;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public interface IngredientService {
    IngredientResponseDto save(IngredientRequestDto ingredient);

    IngredientResponseDto getById(Long id);

    Set<IngredientResponseDto> getAll();
}
