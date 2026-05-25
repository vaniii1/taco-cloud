package ihromovyi.tacocloud.service.ingredient;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientUpdateDto;
import ihromovyi.tacocloud.exception.IngredientNotFoundException;
import ihromovyi.tacocloud.mapper.IngredientMapper;
import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.repository.IngredientRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private static final BigDecimal HALF_VALUE = new BigDecimal("0.5");
    private static final BigDecimal ONE_VALUE = BigDecimal.ONE;
    private static final BigDecimal ONE_AND_HALF_VALUE = new BigDecimal("1.5");
    private static final BigDecimal TWO_VALUE = new BigDecimal("2");

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientResponseDto save(IngredientRequestDto dto) {
        Ingredient ingredient = ingredientMapper.toEntity(dto);
        assignPriceToIngredient(ingredient);
        ingredientRepository.save(ingredient);
        return ingredientMapper.toDto(ingredient);
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
    public Set<IngredientResponseDto> getAllByType(Ingredient.Type type) {
        return ingredientRepository.getAllByType(type)
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
            assignPriceToIngredient(updatedIngredient);
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

    private void assignPriceToIngredient(Ingredient ingredient) {
        switch (ingredient.getType()) {
            case VEGGIE, SAUCE -> ingredient.setPrice(ONE_VALUE);
            case CHEESE -> ingredient.setPrice(ONE_AND_HALF_VALUE);
            case PROTEIN -> ingredient.setPrice(TWO_VALUE);
            default -> ingredient.setPrice(HALF_VALUE);
        }
    }
}
