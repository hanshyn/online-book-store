package hanshyn.onlinebookstore.repository.order;

import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.model.OrderItem;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>,
        JpaSpecificationExecutor<Book> {
    Set<OrderItem> findOrderItemsByOrder_Id(Long orderItemId);

    Set<OrderItem> findOrderItemsByOrder_IdAndOrder_User_Id(Long orderId, Long userId);

    OrderItem findOrderItemByIdAndOrder_IdAndOrder_User_Id(Long itemId, Long orderId, Long userId);
}
