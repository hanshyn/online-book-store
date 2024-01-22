package hanshyn.onlinebookstore.repository.category;

import hanshyn.onlinebookstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
