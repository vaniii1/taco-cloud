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
@SQLDelete(sql = "UPDATE taco_orders SET is_deleted = true WHERE id = ?")
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
    private Date placedAt = new Date();
    @Column(name = "cc_number")
    private String ccNumber;
    @Column(name = "delivered_at")
    private Date deliveredAt;
    @Column(name = "cc_expiration")
    private String ccExpiration;
    @Column(name = "cc_cvv")
    private String ccCvv;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Enumerated(EnumType.STRING)
    private Status status = Status.AWAITING_PAYMENT;
    @Column(name = "price_total")
    private BigDecimal priceTotal;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public enum Status {
        AWAITING_PAYMENT,
        DELIVERED,
        PREPARING,
        ON_THE_WAY,
        CANCELED
    }
}
