package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByStripeSessionId(String stripeSessionId);
}
