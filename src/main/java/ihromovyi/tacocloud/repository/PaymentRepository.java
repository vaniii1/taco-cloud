package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByStripeSessionId(String stripeSessionId);

    Optional<Payment> findByOrderId(Long orderId);

    @Query("SELECT p FROM Payment p WHERE p.user.id = :userId"
            + " AND p.isDeleted = false ORDER BY p.createdAt DESC LIMIT 1")
    Optional<Payment> findLastPaymentByUserId(@Param("userId") Long userId);

    List<Payment> findAllByUserId(Long userId);

    List<Payment> findAllByUserIdAndStatus(Long userId, Payment.Status status);
}
