package hanshyn.onlinebookstore.dto.order;

import hanshyn.onlinebookstore.model.StatusOrder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private StatusOrder.Status status;
}
