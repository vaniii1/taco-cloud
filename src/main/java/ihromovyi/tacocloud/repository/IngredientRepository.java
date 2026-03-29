package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.Ingredient;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Set<Ingredient> getAllByType(Ingredient.Type type);
}
