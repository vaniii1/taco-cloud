package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId"
            + " AND o.isDeleted = false ORDER BY o.placedAt DESC LIMIT 1")
    Optional<Order> findLastOrderByUserId(@Param("userId") Long userId);

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByUserIdAndStatus(Long userId, Order.Status status);
}
