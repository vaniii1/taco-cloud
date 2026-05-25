package ihromovyi.tacocloud.service.cart;

import ihromovyi.tacocloud.dto.cart.CartItemRequestDto;
import ihromovyi.tacocloud.dto.cart.CartItemUpdateQuantityDto;
import ihromovyi.tacocloud.dto.cart.CartResponseDto;
import ihromovyi.tacocloud.exception.ForbiddenItemException;
import ihromovyi.tacocloud.exception.ItemNotFoundException;
import ihromovyi.tacocloud.exception.TacoNotFoundException;
import ihromovyi.tacocloud.exception.UserNotFoundException;
import ihromovyi.tacocloud.mapper.CartMapper;
import ihromovyi.tacocloud.model.Cart;
import ihromovyi.tacocloud.model.CartItem;
import ihromovyi.tacocloud.model.Taco;
import ihromovyi.tacocloud.repository.CartItemRepository;
import ihromovyi.tacocloud.repository.CartRepository;
import ihromovyi.tacocloud.repository.TacoRepository;
import ihromovyi.tacocloud.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartItemRepository itemRepository;
    private final TacoRepository tacoRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartResponseDto getCurrentUserCart() {
        Long currentUserId = userService.getCurrentUser().getId();
        Cart cart = getCartFromDbByUserId(currentUserId);

        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponseDto getCartByUserId(Long userId) {
        Cart cart = getCartFromDbByUserId(userId);

        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponseDto addItemToCart(CartItemRequestDto requestDto) {
        Long currentUserId = userService.getCurrentUser().getId();
        Cart cart = getCartFromDbByUserId(currentUserId);

        Taco taco = getTacoFromDbById(requestDto.tacoId());

        CartItem item = new CartItem();
        item.setTaco(taco);
        item.setQuantity(requestDto.quantity());

        cart.addItem(taco, requestDto.quantity());

        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponseDto updateItemQuantityInCart(
            Long itemId,
            CartItemUpdateQuantityDto requestDto
    ) {
        Long currentUserId = userService.getCurrentUser().getId();

        CartItem item = itemRepository
                .findByIdAndCartUserId(itemId, currentUserId)
                .orElseThrow(
                        () -> new ForbiddenItemException(
                                "You are not allowed to update item quantity of other users."));

        if (requestDto.quantity() == 0) {
            item.getCart().removeItem(item);
        } else {
            item.setQuantity(requestDto.quantity());
        }

        return cartMapper.toResponse(item.getCart());
    }

    @Override
    @Transactional
    public CartResponseDto removeItemFromCart(Long itemId) {

        Long userId = userService.getCurrentUser().getId();

        CartItem item = itemRepository
                .findByIdAndCartUserId(itemId, userId)
                .orElseThrow(
                        () -> new ForbiddenItemException(
                                "You are not allowed to remove item from other carts."));

        item.getCart().removeItem(item);

        return cartMapper.toResponse(item.getCart());
    }

    private Cart getCartFromDbByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException("Cart not found with userId: " + userId));
    }

    private Taco getTacoFromDbById(Long tacoId) {
        return tacoRepository.findById(tacoId).orElseThrow(
                () -> new TacoNotFoundException("Taco not found with id: " + tacoId));
    }

    private CartItem getItemFromDbById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id: " + itemId));
    }

}
