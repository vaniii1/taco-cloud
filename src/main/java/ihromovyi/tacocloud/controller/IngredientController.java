package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.service.ingredient.IngredientService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public IngredientResponseDto addIngredient(
            @RequestBody IngredientRequestDto ingredient) {
        return ingredientService.save(ingredient);
    }

    @GetMapping("/{id}")
    public IngredientResponseDto getIngredientsById(
            @PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @GetMapping
    public Set<IngredientResponseDto> getIngredients() {
        return ingredientService.getAll();
    }
}
