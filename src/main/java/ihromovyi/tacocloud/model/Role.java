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
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE roles SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleName role;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }

    public enum RoleName {
        USER,
        MANAGER,
        DEVELOPER
    }
}
