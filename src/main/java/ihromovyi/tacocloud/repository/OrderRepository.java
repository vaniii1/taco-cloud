package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
