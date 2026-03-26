package ihromovyi.tacocloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE ingredients SET is_deleted = true WHERE id = ?")
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public enum Type {
        WRAP,
        PROTEIN,
        VEGGIE,
        CHEESE,
        SAUCE
    }
}
