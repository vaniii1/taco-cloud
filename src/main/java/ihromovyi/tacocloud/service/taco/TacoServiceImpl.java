package ihromovyi.tacocloud.service.taco;

import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import ihromovyi.tacocloud.dto.taco.TacoUpdateDto;
import ihromovyi.tacocloud.exception.IngredientNotFoundException;
import ihromovyi.tacocloud.exception.TacoNotFoundException;
import ihromovyi.tacocloud.mapper.TacoMapper;
import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.model.Taco;
import ihromovyi.tacocloud.repository.IngredientRepository;
import ihromovyi.tacocloud.repository.TacoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class TacoServiceImpl implements TacoService {
    private final TacoRepository tacoRepository;
    private final IngredientRepository ingredientRepository;
    private final TacoMapper tacoMapper;

    @Override
    @Transactional
    public TacoResponseDto save(TacoRequestDto dto) {
        verifyValidIngredientIds(dto.ingredientIds());
        Taco taco = tacoMapper.toEntity(dto);
        return tacoMapper.toDto(tacoRepository.save(taco));
    }

    @Override
    @Transactional(readOnly = true)
    public TacoResponseDto getById(Long id) {
        Taco taco = getTacoFromDbById(id);
        return tacoMapper.toDto(taco);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TacoResponseDto> getAll() {
        return tacoRepository.findAll()
                .stream()
                .map(tacoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public TacoResponseDto updateById(Long id, TacoUpdateDto dto) {
        Taco taco = getTacoFromDbById(id);
        verifyValidIngredientIds(dto.ingredientIds());
        Taco updatedTaco = tacoMapper.update(taco, dto);
        tacoRepository.save(updatedTaco);
        return tacoMapper.toDto(updatedTaco);
    }

    @Override
    public void deleteById(Long id) {
        if (!tacoRepository.existsById(id)) {
            throw new TacoNotFoundException("Taco not found with id: " + id);
        }
        tacoRepository.deleteById(id);
    }

    private void verifyValidIngredientIds(List<Long> ids) {
        if (ids != null) {
            List<Ingredient> foundIngredients = ingredientRepository
                    .findAllById(ids);
            List<Long> foundIds = foundIngredients.stream()
                    .map(Ingredient::getId)
                    .toList();
            List<Long> missingIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            if (!missingIds.isEmpty()) {
                throw new IngredientNotFoundException(
                        "Ingredients not found with ids: " + missingIds);
            }
        }
    }

    private Taco getTacoFromDbById(Long id) {
        return tacoRepository.findById(id).orElseThrow(
                () -> new TacoNotFoundException("Taco not found with id: " + id));
    }
}
