package ihromovyi.tacocloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE ingredients SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type = Type.UNKNOWN;
    private BigDecimal price = new BigDecimal("0.5");
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public Ingredient(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Ingredient(Long id) {
        this.id = id;
    }

    public enum Type {
        UNKNOWN,
        WRAP,
        PROTEIN,
        VEGGIE,
        CHEESE,
        SAUCE
    }
}
