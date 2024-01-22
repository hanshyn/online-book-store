package hanshyn.onlinebookstore.service.book;

import hanshyn.onlinebookstore.dto.book.BookDto;
import hanshyn.onlinebookstore.dto.book.BookSearchParameters;
import hanshyn.onlinebookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    BookDto updateById(CreateBookRequestDto requestDto, Long id);

    List<BookDto> search(BookSearchParameters parameters);

    List<BookDto> findAllByCategoryId(Long categoryId, Pageable pageable);
}
