package hanshyn.onlinebookstore.repository;

import hanshyn.onlinebookstore.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> getAll();
}
