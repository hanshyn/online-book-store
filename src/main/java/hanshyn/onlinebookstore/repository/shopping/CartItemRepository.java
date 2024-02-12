package hanshyn.onlinebookstore.repository.shopping;

import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.model.CartItem;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartItemRepository extends JpaRepository<CartItem, Long>,
        JpaSpecificationExecutor<Book> {
    Set<CartItem> findByShoppingCartId(Long shoppingCart);
}
