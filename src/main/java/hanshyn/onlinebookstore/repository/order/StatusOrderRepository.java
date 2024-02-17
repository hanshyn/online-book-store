package hanshyn.onlinebookstore.repository.order;

import hanshyn.onlinebookstore.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusOrderRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByStatus(OrderStatus.Status status);
}
