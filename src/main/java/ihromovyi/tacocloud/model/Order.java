package ihromovyi.tacocloud.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "delivery_name")
    private String deliveryName;
    @Column(name = "delivery_street")
    private String deliveryStreet;
    @Column(name = "delivery_city")
    private String deliveryCity;
    @Column(name = "delivery_state")
    private String deliveryState;
    @Column(name = "delivery_zip")
    private String deliveryZip;
    @Column(name = "placed_at")
    private LocalDateTime placedAt = LocalDateTime.now();
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status = Status.AWAITING_PAYMENT;
    @Column(name = "status_changed_at")
    private LocalDateTime statusChangedAt = LocalDateTime.now();
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public enum Status {
        AWAITING_PAYMENT,
        PREPARING,
        ON_THE_WAY,
        DELIVERED,
        CANCELED;

        private static final Map<Status, Set<Status>> ALLOWED_TRANSITIONS = Map.of(
                AWAITING_PAYMENT, Set.of(PREPARING, CANCELED),
                PREPARING, Set.of(ON_THE_WAY, CANCELED),
                ON_THE_WAY, Set.of(DELIVERED, CANCELED),
                DELIVERED, Set.of(),
                CANCELED, Set.of()
        );

        public boolean canTransitionTo(Status next) {
            return ALLOWED_TRANSITIONS.get(this).contains(next);
        }
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        item.setOrder(this);
    }
}
