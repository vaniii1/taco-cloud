package ihromovyi.tacocloud.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "Taco")
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private final String name;
    @ManyToMany
    @JoinTable(name="taco_ingredient",
            joinColumns= @JoinColumn(name="taco", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="ingerient", referencedColumnName="id")
    )
    private final List<Ingredient> ingredients;
}
