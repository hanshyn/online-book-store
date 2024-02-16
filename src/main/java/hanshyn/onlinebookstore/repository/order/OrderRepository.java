package hanshyn.onlinebookstore.repository.order;

import hanshyn.onlinebookstore.model.Order;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "status"})
    List<Order> findByUserId(Long id, Pageable pageable);

    Order findOrderByIdAndUserId(Long orderId, Long userId);
}
