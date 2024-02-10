package hanshyn.onlinebookstore.service.cart;

import hanshyn.onlinebookstore.dto.cart.CartItemsRequestDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsResponseDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsUpdateRequestDto;
import hanshyn.onlinebookstore.dto.cart.ShoppingCartDto;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Authentication authentication);

    CartItemsResponseDto saveBookToShoppingCart(Authentication authentication,
                                                 CartItemsRequestDto cartItemsRequestDto);

    CartItemsResponseDto updateQuantity(
                                        CartItemsUpdateRequestDto cartItemsUpdateRequestDto,
                                        Long id);

    void deleteCartItems(Long id);
}
