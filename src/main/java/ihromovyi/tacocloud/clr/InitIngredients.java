package ihromovyi.tacocloud.clr;

import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.repository.IngredientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitIngredients implements CommandLineRunner {
    private final IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) {
        if (ingredientRepository.count() == 0) {
            ingredientRepository.saveAll(getDefaultIngredients());
        }
    }

    private List<Ingredient> getDefaultIngredients() {
        return List.of(
                new Ingredient("Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("Tomatoes", Ingredient.Type.VEGGIE),
                new Ingredient("Lettuce", Ingredient.Type.VEGGIE),
                new Ingredient("Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("Mozzarella", Ingredient.Type.CHEESE),
                new Ingredient("Salsa", Ingredient.Type.SAUCE),
                new Ingredient("Sour Cream", Ingredient.Type.SAUCE)
        );
    }
}
