package ihromovyi.tacocloud.service.ingredient;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientUpdateDto;
import ihromovyi.tacocloud.exception.IngredientNotFoundException;
import ihromovyi.tacocloud.mapper.IngredientMapper;
import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.repository.IngredientRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientResponseDto save(IngredientRequestDto dto) {
        Ingredient savedEntity =
                ingredientRepository.save(ingredientMapper.toEntity(dto));
        return ingredientMapper.toDto(savedEntity);
    }

    @Override
    public IngredientResponseDto getById(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isPresent()) {
            return ingredientMapper.toDto(optionalIngredient.get());
        }
        throw new IngredientNotFoundException("Ingredient not found with id: " + id);
    }

    @Override
    public Set<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public IngredientResponseDto updateById(Long id, IngredientUpdateDto dto) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isPresent()) {
            Ingredient updatedIngredient =
                    ingredientMapper.update(optionalIngredient.get(), dto);
            ingredientRepository.save(updatedIngredient);
            return ingredientMapper.toDto(updatedIngredient);
        }
        throw new IngredientNotFoundException("Ingredient not found with id: " + id);
    }

    @Override
    public void deleteById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() ->
                        new IngredientNotFoundException("Ingredient not found with id: " + id));
        ingredientRepository.delete(ingredient);
    }
}
