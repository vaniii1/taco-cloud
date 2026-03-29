package ihromovyi.tacocloud.service.tacoorder;

import ihromovyi.tacocloud.dto.tacoorder.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.tacoorder.TacoOrderUpdateDto;
import ihromovyi.tacocloud.exception.TacoNotFoundException;
import ihromovyi.tacocloud.exception.TacoOrderNotFoundException;
import ihromovyi.tacocloud.mapper.TacoOrderMapper;
import ihromovyi.tacocloud.model.Taco;
import ihromovyi.tacocloud.model.TacoOrder;
import ihromovyi.tacocloud.repository.TacoOrderRepository;
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
public class TacoOrderServiceImpl implements TacoOrderService {
    private final TacoOrderRepository tacoOrderRepository;
    private final TacoOrderMapper tacoOrderMapper;
    private final TacoRepository tacoRepository;

    @Override
    public TacoOrderResponseDto save(TacoOrderRequestDto dto) {
        verifyValidTacoIds(dto.tacoIds());
        TacoOrder entity = tacoOrderMapper.toEntity(dto);
        return tacoOrderMapper.toDto(tacoOrderRepository.save(entity));
    }

    @Override
    public TacoOrderResponseDto getById(Long id) {
        Optional<TacoOrder> optionalTacoOrder = tacoOrderRepository.findById(id);
        if (optionalTacoOrder.isPresent()) {
            return tacoOrderMapper.toDto(optionalTacoOrder.get());
        }
        throw new TacoOrderNotFoundException("TacoOrder not found with id: " + id);
    }

    @Override
    public Set<TacoOrderResponseDto> getAll() {
        return tacoOrderRepository.findAll()
                .stream()
                .map(tacoOrderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public TacoOrderResponseDto updateById(Long id, TacoOrderUpdateDto dto) {
        Optional<TacoOrder> optionalTacoOrder = tacoOrderRepository.findById(id);
        if (optionalTacoOrder.isPresent()) {
            verifyValidTacoIds(dto.tacoIds());
            TacoOrder updatedTaco = tacoOrderMapper.update(optionalTacoOrder.get(), dto);
            tacoOrderRepository.save(updatedTaco);
            return tacoOrderMapper.toDto(updatedTaco);
        }
        throw new TacoOrderNotFoundException("TacoOrder not found with id: " + id);
    }

    @Override
    public void deleteById(Long id) {
        tacoOrderRepository.findById(id)
                .orElseThrow(() ->
                        new TacoOrderNotFoundException("TacoOrder not found with id: " + id));
        tacoOrderRepository.deleteById(id);
    }

    private void verifyValidTacoIds(Set<Long> ids) {
        if (ids != null) {
            List<Taco> foundTacos = tacoRepository
                    .findAllById(ids);
            Set<Long> foundIds = foundTacos.stream()
                    .map(Taco::getId)
                    .collect(Collectors.toSet());
            Set<Long> missingIds = ids.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());
            if (!missingIds.isEmpty()) {
                throw new TacoNotFoundException(
                        "Tacos not found with ids: " + missingIds);
            }
        }
    }
}
