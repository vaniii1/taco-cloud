package ihromovyi.tacocloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE order_items SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Taco taco;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private int quantity;
    private BigDecimal subtotal;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;
}
