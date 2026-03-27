package ihromovyi.tacocloud.service.taco;

import ihromovyi.tacocloud.dto.taco.TacoRequestDto;
import ihromovyi.tacocloud.dto.taco.TacoResponseDto;
import ihromovyi.tacocloud.exception.TacoNotFoundException;
import ihromovyi.tacocloud.mapper.TacoMapper;
import ihromovyi.tacocloud.model.Taco;
import ihromovyi.tacocloud.repository.TacoRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TacoServiceImpl implements TacoService {
    private final TacoRepository tacoRepository;
    private final TacoMapper tacoMapper;

    @Override
    public TacoResponseDto save(TacoRequestDto requestDto) {
        Taco entity = tacoMapper.toEntity(requestDto);
        return tacoMapper.toDto(tacoRepository.save(entity));
    }

    @Override
    public TacoResponseDto getById(Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if (optionalTaco.isEmpty()) {
            throw new TacoNotFoundException("Ingredient not found with id: " + id);
        }
        return tacoMapper.toDto(optionalTaco.get());
    }

    @Override
    public Set<TacoResponseDto> getAll() {
        return tacoRepository.findAll()
                .stream()
                .map(tacoMapper::toDto)
                .collect(Collectors.toSet());
    }
}
