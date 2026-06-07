package ihromovyi.tacocloud.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@NoArgsConstructor
@Table(name = "carts")
@SQLDelete(sql = "UPDATE carts SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public void addItem(Taco taco, int quantity) {
        for (CartItem cartItem : items) {
            if (cartItem.getTaco().getId().equals(taco.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setTaco(taco);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(this);

        items.add(newCartItem);
    }

    public void removeItem(CartItem cartItem) {
        items.remove(cartItem);
        cartItem.setCart(null);
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
