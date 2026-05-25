package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndCartUserId(Long itemId, Long userId);
}
