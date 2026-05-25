package ihromovyi.tacocloud.service.order;

import ihromovyi.tacocloud.dto.order.TacoOrderRequestDto;
import ihromovyi.tacocloud.dto.order.TacoOrderResponseDto;
import ihromovyi.tacocloud.dto.order.TacoOrderUpdateDto;
import ihromovyi.tacocloud.exception.TacoNotFoundException;
import ihromovyi.tacocloud.exception.TacoOrderNotFoundException;
import ihromovyi.tacocloud.mapper.OrderMapper;
import ihromovyi.tacocloud.model.Order;
import ihromovyi.tacocloud.model.Taco;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.OrderRepository;
import ihromovyi.tacocloud.repository.TacoRepository;
import ihromovyi.tacocloud.service.user.UserService;
import java.util.List;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository tacoOrderRepository;
    private final OrderMapper orderMapper;
    private final TacoRepository tacoRepository;
    private final UserService userService;

    @Override
    @Transactional
    public TacoOrderResponseDto save(TacoOrderRequestDto dto) {
        verifyValidTacoIds(dto.itemIds());
        Order order = orderMapper.toEntity(dto);
        order.setUser(new User(userService.getCurrentUser().getId()));
        return orderMapper.toDto(tacoOrderRepository.save(order));
    }

    @Override
    public TacoOrderResponseDto getById(Long id) {
        Optional<Order> optionalTacoOrder = tacoOrderRepository.findById(id);
        if (optionalTacoOrder.isPresent()) {
            return orderMapper.toDto(optionalTacoOrder.get());
        }
        throw new TacoOrderNotFoundException("TacoOrder not found with id: " + id);
    }

    @Override
    public Set<TacoOrderResponseDto> getAll() {
        return tacoOrderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public TacoOrderResponseDto updateById(Long id, TacoOrderUpdateDto dto) {
        Optional<Order> optionalTacoOrder = tacoOrderRepository.findById(id);
        if (optionalTacoOrder.isPresent()) {
            verifyValidTacoIds(dto.itemIds());
            Order updatedTaco = orderMapper.update(optionalTacoOrder.get(), dto);
            tacoOrderRepository.save(updatedTaco);
            return orderMapper.toDto(updatedTaco);
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
