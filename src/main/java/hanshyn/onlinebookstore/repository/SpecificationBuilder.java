package hanshyn.onlinebookstore.repository;

import hanshyn.onlinebookstore.dto.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters searchParameters);
}
