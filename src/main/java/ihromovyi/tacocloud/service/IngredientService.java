package ihromovyi.tacocloud.service;

import ihromovyi.tacocloud.model.Ingredient;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface IngredientService {
    void addIngredient(Ingredient ingredient);

    List<Ingredient> getIngredients();
}
