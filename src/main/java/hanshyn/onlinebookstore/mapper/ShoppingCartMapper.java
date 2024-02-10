package hanshyn.onlinebookstore.mapper;

import hanshyn.onlinebookstore.config.MapperConfig;
import hanshyn.onlinebookstore.dto.cart.ShoppingCartDto;
import hanshyn.onlinebookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems", ignore = true)
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
