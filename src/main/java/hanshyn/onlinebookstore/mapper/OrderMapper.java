package hanshyn.onlinebookstore.mapper;

import hanshyn.onlinebookstore.config.MapperConfig;
import hanshyn.onlinebookstore.dto.order.OrderResponseDto;
import hanshyn.onlinebookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "status.status",target = "status")
    OrderResponseDto toDto(Order order);

    /*
    @AfterMapping
    default void setOrderItems(@MappingTarget OrderResponseDto orderResponseDto, Order order) {
        Set<Long> orderItem = order.getOrderItems().stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet());
       // System.out.println(orderItem);
        //orderResponseDto.setOrderItems();
    }
    */
}
