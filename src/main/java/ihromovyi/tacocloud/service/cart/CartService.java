package ihromovyi.tacocloud.service.cart;

import ihromovyi.tacocloud.dto.cart.CartItemRequestDto;
import ihromovyi.tacocloud.dto.cart.CartItemUpdateQuantityDto;
import ihromovyi.tacocloud.dto.cart.CartResponseDto;

public interface CartService {
    CartResponseDto getCurrentUserCart();

    CartResponseDto getCartByUserId(Long userId);

    CartResponseDto addItemToCart(CartItemRequestDto requestDto);

    CartResponseDto updateItemQuantityInCart(Long itemId, CartItemUpdateQuantityDto requestDto);

    CartResponseDto removeItemFromCart(Long itemId);
}
