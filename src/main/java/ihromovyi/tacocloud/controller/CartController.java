package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.cart.CartItemRequestDto;
import ihromovyi.tacocloud.dto.cart.CartItemUpdateQuantityDto;
import ihromovyi.tacocloud.dto.cart.CartResponseDto;
import ihromovyi.tacocloud.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto getCurrentCart() {
        return cartService.getCurrentUserCart();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/add_item")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponseDto addItemToCurrentCart(@Valid @RequestBody CartItemRequestDto dto) {
        return cartService.addItemToCart(dto);
    }

    @PatchMapping("/update_quantity/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto updateItemQuantity(
            @PathVariable Long itemId, @Valid @RequestBody CartItemUpdateQuantityDto dto) {
        return cartService.updateItemQuantityInCart(itemId, dto);
    }

    @DeleteMapping("/remove_item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto deleteItemFromCurrentCart(@PathVariable Long itemId) {
        return cartService.removeItemFromCart(itemId);
    }
}
