package ihromovyi.tacocloud.service.ingredient;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.exception.IngredientNotFoundException;
import ihromovyi.tacocloud.mapper.IngredientMapper;
import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.repository.IngredientRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientResponseDto save(IngredientRequestDto ingredientRequestDto) {
        Ingredient savedEntity =
                ingredientRepository.save(ingredientMapper.toEntity(ingredientRequestDto));
        return ingredientMapper.toDto(savedEntity);
    }

    @Override
    public IngredientResponseDto getById(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (!optionalIngredient.isPresent()) {
            throw new IngredientNotFoundException("Ingredient not found with id: " + id);
        }
        return ingredientMapper.toDto(optionalIngredient.get());
    }

    @Override
    public Set<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toSet());
    }
}
