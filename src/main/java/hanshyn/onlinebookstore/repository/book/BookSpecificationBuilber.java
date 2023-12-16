package hanshyn.onlinebookstore.repository.book;

import hanshyn.onlinebookstore.dto.book.BookSearchParameters;
import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.repository.SpecificationBuilder;
import hanshyn.onlinebookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilber implements SpecificationBuilder<Book> {
    private static final String AUTHOR = "author";
    private static final String DESCRIPTION = "description";
    private static final String ISBN = "isbn";
    private static final String TITLE = "title";

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);

        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(TITLE)
                    .getSpecification(searchParameters.titles()));
        }

        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(AUTHOR)
                    .getSpecification(searchParameters.authors()));
        }

        if (searchParameters.isbns() != null && searchParameters.isbns().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(ISBN)
                    .getSpecification(searchParameters.isbns()));
        }

        if (searchParameters.descriptions() != null && searchParameters.descriptions().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(DESCRIPTION)
                    .getSpecification(searchParameters.descriptions()));
        }

        return spec;
    }
}
