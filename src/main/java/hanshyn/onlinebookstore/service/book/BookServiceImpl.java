package hanshyn.onlinebookstore.service.book;

import hanshyn.onlinebookstore.dto.book.BookDto;
import hanshyn.onlinebookstore.dto.book.BookSearchParameters;
import hanshyn.onlinebookstore.dto.book.CreateBookRequestDto;
import hanshyn.onlinebookstore.exception.EntityNotFoundException;
import hanshyn.onlinebookstore.mapper.BookMapper;
import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.model.Category;
import hanshyn.onlinebookstore.repository.book.BookRepository;
import hanshyn.onlinebookstore.repository.book.BookSpecificationBuilber;
import hanshyn.onlinebookstore.repository.category.CategoryRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilber bookSpecificationBuilber;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        Set<Category> categories = getCategoriesByIds(requestDto);
        book.setCategories(categories);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateById(CreateBookRequestDto requestDto, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );

        Set<Category> categories = getCategoriesByIds(requestDto);

        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setPrice(requestDto.getPrice());
        book.setDescription(requestDto.getDescription());
        book.setCoverImage(requestDto.getCoverImage());
        book.setCategories(categories);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParameters parameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilber.build(parameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return bookRepository.findBooksByCategoryId(categoryId, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    private Set<Category> getCategoriesByIds(CreateBookRequestDto requestDto) {
        return requestDto.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId).orElseThrow(
                        () -> new EntityNotFoundException("Can't find category by id:"
                                + categoryId)))
                .collect(Collectors.toSet());
    }
}
