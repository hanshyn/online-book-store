package hanshyn.onlinebookstore.dto.order;

import hanshyn.onlinebookstore.model.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long id,
        Long userId,
        Set<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        OrderStatus.Status status

) {
}
