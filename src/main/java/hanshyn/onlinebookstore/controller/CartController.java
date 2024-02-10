package hanshyn.onlinebookstore.controller;

import hanshyn.onlinebookstore.dto.cart.CartItemsRequestDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsResponseDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsUpdateRequestDto;
import hanshyn.onlinebookstore.dto.cart.ShoppingCartDto;
import hanshyn.onlinebookstore.service.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart", description = "Endpoints for Cart")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cart")
public class CartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Retrieve user's shopping cart",
            description = "Retrieve user's shopping cart")
    @GetMapping
    public ShoppingCartDto getShoppingCartUser(Authentication authentication) {
        return shoppingCartService.getShoppingCart(authentication);
    }

    @Operation(summary = "Add new cart item in the store",
            description = "Add new cart item in the store")
    @PostMapping
    public CartItemsResponseDto createCartItem(Authentication authentication,
            @RequestBody @Valid CartItemsRequestDto cartItemsRequestDto) {
        return shoppingCartService.saveBookToShoppingCart(authentication, cartItemsRequestDto);
    }

    @Operation(summary = "Update quantity of a book",
            description = "Update quantity of a book in the shopping cart")
    @PutMapping("/cart-items/{id}")
    public CartItemsResponseDto updateCartItemsById(
            @RequestBody CartItemsUpdateRequestDto cartItemsUpdateRequestDto,
            @PathVariable Long id) {
        return shoppingCartService.updateQuantity(cartItemsUpdateRequestDto, id);
    }

    @Operation(summary = "Delete cart items",
            description = "Delete cart items by Id")
    @DeleteMapping("/cart-items/{id}")
    public void deleteCartItemById(@PathVariable Long id) {
        shoppingCartService.deleteCartItems(id);
    }
}
