package hanshyn.onlinebookstore.service;

import hanshyn.onlinebookstore.dto.BookDto;
import hanshyn.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll();

    BookDto getById(Long id);
}
