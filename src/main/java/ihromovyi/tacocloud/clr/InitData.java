package ihromovyi.tacocloud.clr;

import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.model.Taco;
import ihromovyi.tacocloud.repository.IngredientRepository;
import ihromovyi.tacocloud.repository.TacoRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @Override
    public void run(String... args) {
        if (ingredientRepository.count() == 0) {
            ingredientRepository.saveAll(getDefaultIngredients());
        }
        if (tacoRepository.count() == 0) {
            tacoRepository.saveAll(getDefaultTacos());
        }
    }

    private List<Ingredient> getDefaultIngredients() {
        return List.of(
                new Ingredient("Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("Bacon", Ingredient.Type.PROTEIN),
                new Ingredient("Jalapenos", Ingredient.Type.VEGGIE),
                new Ingredient("Tomatoes", Ingredient.Type.VEGGIE),
                new Ingredient("Lettuce", Ingredient.Type.VEGGIE),
                new Ingredient("Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("Mozzarella", Ingredient.Type.CHEESE),
                new Ingredient("Salsa", Ingredient.Type.SAUCE),
                new Ingredient("Sour Cream", Ingredient.Type.SAUCE)
        );
    }

    private List<Taco> getDefaultTacos() {
        return List.of(
                new Taco("Basic Mexican", Set.of(
                        new Ingredient(1L), new Ingredient(3L),
                        new Ingredient(7L), new Ingredient(12L))),
                new Taco("Spicy Mexican", Set.of(
                        new Ingredient(2L), new Ingredient(3L),
                        new Ingredient(6L), new Ingredient(9L))),
                new Taco("For Kids", Set.of(
                        new Ingredient(1L), new Ingredient(4L),
                        new Ingredient(9L), new Ingredient(12L)))
        );
    }
}
