package ihromovyi.tacocloud.repository;

import ihromovyi.tacocloud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Iterable<Long> id(Long id);
}
