package ihromovyi.tacocloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@NoArgsConstructor
@Table(name = "payments")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE payments SET is_deleted = true WHERE id = ?")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;
    @Column(name = "stripe_session_id")
    private String stripeSessionId;
    private BigDecimal amount;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public enum Status {
        PENDING,
        COMPLETED,
        FAILED
    }
}
