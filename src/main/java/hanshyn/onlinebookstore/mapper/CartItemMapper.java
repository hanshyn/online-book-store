package hanshyn.onlinebookstore.mapper;

import hanshyn.onlinebookstore.config.MapperConfig;
import hanshyn.onlinebookstore.dto.cart.CartItemsRequestDto;
import hanshyn.onlinebookstore.dto.cart.CartItemsResponseDto;
import hanshyn.onlinebookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(source = "cartItem.book.id", target = "bookId")
    @Mapping(source = "cartItem.book.title", target = "bookTitle")
    CartItemsResponseDto toDo(CartItem cartItem);

    CartItem toModel(CartItemsRequestDto cartItemsRequestDto);
}
