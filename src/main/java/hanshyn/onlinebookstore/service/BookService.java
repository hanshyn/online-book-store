package hanshyn.onlinebookstore.service;

import hanshyn.onlinebookstore.dto.BookDto;
import hanshyn.onlinebookstore.dto.BookSearchParameters;
import hanshyn.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto updateById(CreateBookRequestDto requestDto, Long id);

    List<BookDto> search(BookSearchParameters parameters);
}
