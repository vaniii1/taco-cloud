package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
