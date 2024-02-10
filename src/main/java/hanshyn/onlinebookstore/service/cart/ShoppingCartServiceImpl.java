package hanshyn.onlinebookstore.service.cart;

import hanshyn.onlinebookstore.dto.cart.CartItemsRequestDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsResponseDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsUpdateRequestDto;
import hanshyn.onlinebookstore.dto.cart.ShoppingCartDto;
import hanshyn.onlinebookstore.exception.EntityNotFoundException;
import hanshyn.onlinebookstore.mapper.CartItemMapper;
import hanshyn.onlinebookstore.mapper.ShoppingCartMapper;
import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.model.CartItem;
import hanshyn.onlinebookstore.model.ShoppingCart;
import hanshyn.onlinebookstore.model.User;
import hanshyn.onlinebookstore.repository.book.BookRepository;
import hanshyn.onlinebookstore.repository.shopping.CartItemRepository;
import hanshyn.onlinebookstore.repository.shopping.ShoppingCartRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new RuntimeException("Can't found user: user")
        );

        Set<CartItem> cartItem = cartItemRepository.findByShoppingCartId(shoppingCart.getId());

        Set<CartItemsResponseDto> cartItemsResponseDtos = cartItem.stream()
                .map(cartItemMapper::toDo)
                .collect(Collectors.toSet());

        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        shoppingCartDto.setCartItems(cartItemsResponseDtos);

        return shoppingCartDto;
    }

    @Override
    public CartItemsResponseDto saveBookToShoppingCart(Authentication authentication,
                                                       CartItemsRequestDto cartItemsRequestDto) {
        User user = (User) authentication.getPrincipal();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewShoppingCart(user));

        Book book = bookRepository.findById(cartItemsRequestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book by id:"
                        + cartItemsRequestDto.getBookId() + "not found"));

        CartItem cartItem = cartItemMapper.toModel(cartItemsRequestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);

        CartItem saveCartItem = cartItemRepository.save(cartItem);
        Set<CartItem> cartItems = cartItemRepository.findByShoppingCartId(shoppingCart.getId());
        cartItems.add(saveCartItem);
        shoppingCart.setCartItems(cartItems);
        shoppingCartRepository.save(shoppingCart);
        return cartItemMapper.toDo(cartItem);
    }

    private ShoppingCart createNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public CartItemsResponseDto updateQuantity(CartItemsUpdateRequestDto cartItemsUpdateRequestDto,
                                               Long id) {

        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't found cart item:by id " + id));

        cartItem.setQuantity(cartItemsUpdateRequestDto.getQuantity());

        return cartItemMapper.toDo(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItems(Long id) {
        cartItemRepository.deleteById(id);
    }
}
