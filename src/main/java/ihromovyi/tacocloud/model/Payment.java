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
import java.util.Date;
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
    @Column(name = "stripe_payment_intent_id")
    private String stripePaymentIntentId;
    private BigDecimal amount;
    private String currency;
    @Column(name = "created_at")
    private Date createdAt = new Date();
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public enum Status {
        PENDING,
        CONFIRMED,
        DECLINED
    }
}
