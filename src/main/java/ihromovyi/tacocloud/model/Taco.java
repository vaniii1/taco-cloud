package ihromovyi.tacocloud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE tacos SET is_deleted = true WHERE id = ?")
@Table(name = "tacos")
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name = "taco_ingredient",
            joinColumns = @JoinColumn(name = "taco", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingerient", referencedColumnName = "id")
    )
    private Set<Ingredient> ingredients;

    public Taco(Long id) {
        this.id = id;
    }
}
