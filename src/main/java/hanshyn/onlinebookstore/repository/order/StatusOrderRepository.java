package hanshyn.onlinebookstore.repository.order;

import hanshyn.onlinebookstore.model.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusOrderRepository extends JpaRepository<StatusOrder, Long> {
    StatusOrder findByStatus(StatusOrder.Status status);
}
