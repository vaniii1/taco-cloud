package ihromovyi.tacocloud.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE taco_orders SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "taco_orders")
public class TacoOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCvv;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Taco> tacos = new HashSet<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
