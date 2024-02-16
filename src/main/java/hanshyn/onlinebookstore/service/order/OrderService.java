package hanshyn.onlinebookstore.service.order;

import hanshyn.onlinebookstore.dto.order.OrderItemResponseDto;
import hanshyn.onlinebookstore.dto.order.OrderPlaceRequestDto;
import hanshyn.onlinebookstore.dto.order.OrderResponseDto;
import hanshyn.onlinebookstore.dto.order.OrderUpdateStatusRequestDto;
import hanshyn.onlinebookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(OrderPlaceRequestDto orderPlaceRequestDto);

    List<OrderResponseDto> getHistoryOrder(User user, Pageable pageable);

    OrderResponseDto updateStatusOrderById(
            Long orderId,
            OrderUpdateStatusRequestDto orderUpdateStatusRequestDto);

    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId);

    OrderItemResponseDto getOrderItemById(Long orderId, Long itemId);
}
