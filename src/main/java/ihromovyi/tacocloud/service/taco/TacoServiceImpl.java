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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TacoServiceImpl implements TacoService {
    private final TacoRepository tacoRepository;
    private final IngredientRepository ingredientRepository;
    private final TacoMapper tacoMapper;

    @Override
    public TacoResponseDto save(TacoRequestDto dto) {
        verifyValidIngredientIds(dto.ingredientIds());
        Taco entity = tacoMapper.toEntity(dto);
        return tacoMapper.toDto(tacoRepository.save(entity));
    }

    @Override
    public TacoResponseDto getById(Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if (optionalTaco.isPresent()) {
            return tacoMapper.toDto(optionalTaco.get());
        }
        throw new TacoNotFoundException("Taco not found with id: " + id);
    }

    @Override
    public Set<TacoResponseDto> getAll() {
        return tacoRepository.findAll()
                .stream()
                .map(tacoMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public TacoResponseDto updateById(Long id, TacoUpdateDto dto) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if (optionalTaco.isPresent()) {
            verifyValidIngredientIds(dto.ingredientIds());
            Taco updatedTaco = tacoMapper.update(optionalTaco.get(), dto);
            tacoRepository.save(updatedTaco);
            return tacoMapper.toDto(updatedTaco);
        }
        throw new TacoNotFoundException("Taco not found with id: " + id);
    }

    @Override
    public void deleteById(Long id) {
        tacoRepository.findById(id)
                .orElseThrow(() ->
                        new TacoNotFoundException("Taco not found with id: " + id));
        tacoRepository.deleteById(id);
    }

    private void verifyValidIngredientIds(Set<Long> ids) {
        if (ids != null) {
            List<Ingredient> foundIngredients = ingredientRepository
                    .findAllById(ids);
            Set<Long> foundIds = foundIngredients.stream()
                    .map(Ingredient::getId)
                    .collect(Collectors.toSet());
            Set<Long> missingIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());
            if (!missingIds.isEmpty()) {
                throw new IngredientNotFoundException(
                        "Ingredients not found with ids: " + missingIds);
            }
        }
    }
}
