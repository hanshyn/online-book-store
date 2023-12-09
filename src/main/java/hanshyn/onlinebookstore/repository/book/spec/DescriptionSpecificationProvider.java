package hanshyn.onlinebookstore.repository.book.spec;

import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DescriptionSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "description";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("description").in(Arrays.stream(params).toArray());
    }
}
