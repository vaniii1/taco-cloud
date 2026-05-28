package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.ingredient.IngredientRequestDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientResponseDto;
import ihromovyi.tacocloud.dto.ingredient.IngredientUpdateDto;
import ihromovyi.tacocloud.model.Ingredient;
import ihromovyi.tacocloud.service.ingredient.IngredientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PreAuthorize("hasAuthority('DEVELOPER')")
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
    public List<IngredientResponseDto> getIngredientsByType(
            @RequestParam(required = false) Ingredient.Type type) {
        return (type != null)
                ? ingredientService.getAllByType(type)
                : ingredientService.getAll();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public IngredientResponseDto updateIngredient(
            @PathVariable Long id,
            @RequestBody IngredientUpdateDto ingredient) {
        return ingredientService.updateById(id, ingredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public void deleteIngredient(
            @PathVariable Long id) {
        ingredientService.deleteById(id);
    }
}
