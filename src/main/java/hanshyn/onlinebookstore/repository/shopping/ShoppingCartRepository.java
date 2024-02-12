package hanshyn.onlinebookstore.repository.shopping;

import hanshyn.onlinebookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>,
        JpaSpecificationExecutor<ShoppingCart> {
    Optional<ShoppingCart> findByUserId(@Param("id") Long id);
}
