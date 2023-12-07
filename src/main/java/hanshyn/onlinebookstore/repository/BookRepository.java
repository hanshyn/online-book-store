package hanshyn.onlinebookstore.repository;

import hanshyn.onlinebookstore.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
