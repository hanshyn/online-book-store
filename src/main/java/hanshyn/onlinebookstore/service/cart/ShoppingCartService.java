package hanshyn.onlinebookstore.service.cart;

import hanshyn.onlinebookstore.dto.cart.CartItemsRequestDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsResponseDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsUpdateRequestDto;
import hanshyn.onlinebookstore.dto.cart.ShoppingCartDto;
import hanshyn.onlinebookstore.model.User;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Authentication authentication);

    CartItemsResponseDto saveBookToShoppingCart(User user,
                                                CartItemsRequestDto cartItemsRequestDto);

    CartItemsResponseDto updateQuantity(
                                        CartItemsUpdateRequestDto cartItemsUpdateRequestDto,
                                        Long id);

    void deleteCartItems(Long id);
}
