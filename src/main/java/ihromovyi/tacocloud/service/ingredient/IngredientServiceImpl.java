package ihromovyi.tacocloud.service.ingredient;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientUpdateDto;
import ihromovyi.tacocloud.exception.IngredientNotFoundException;
import ihromovyi.tacocloud.mapper.IngredientMapper;
import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.repository.IngredientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientResponseDto save(IngredientRequestDto dto) {
        Ingredient ingredient = ingredientMapper.toEntity(dto);
        ingredient.assignPriceByType();
        ingredientRepository.save(ingredient);
        return ingredientMapper.toDto(ingredient);
    }

    @Override
    public IngredientResponseDto getById(Long id) {
        Ingredient ingredient = getIngredientById(id);
        return ingredientMapper.toDto(ingredient);
    }

    @Override
    public List<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    @Override
    public List<IngredientResponseDto> getAllByType(Ingredient.Type type) {
        return ingredientRepository.getAllByType(type)
                .stream()
                .map(ingredientMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public IngredientResponseDto updateById(Long id, IngredientUpdateDto dto) {
        Ingredient ingredient = getIngredientById(id);
        Ingredient updatedIngredient =
                ingredientMapper.update(ingredient, dto);
        updatedIngredient.assignPriceByType();
        return ingredientMapper.toDto(updatedIngredient);
    }

    @Override
    public void deleteById(Long id) {
        Ingredient ingredient = getIngredientById(id);
        ingredientRepository.delete(ingredient);
    }

    private Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(
                        "Ingredient not found with id: " + id));
    }
}
