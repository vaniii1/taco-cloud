package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoOrderRepository extends JpaRepository<TacoOrder, Long> {
}
