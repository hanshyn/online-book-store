package hanshyn.onlinebookstore.service;

import hanshyn.onlinebookstore.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> getAll();
}
