package hanshyn.onlinebookstore.service.book;

import static org.mockito.Mockito.when;

import hanshyn.onlinebookstore.dto.book.BookDto;
import hanshyn.onlinebookstore.dto.book.CreateBookRequestDto;
import hanshyn.onlinebookstore.exception.EntityNotFoundException;
import hanshyn.onlinebookstore.mapper.BookMapper;
import hanshyn.onlinebookstore.model.Book;
import hanshyn.onlinebookstore.model.Category;
import hanshyn.onlinebookstore.repository.book.BookRepository;
import hanshyn.onlinebookstore.repository.category.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 100L;
    private static final String TITLE = "Book title";
    private static final String AUTHOR = "Author Bob";
    private static final String ISBN = "9781234567897";
    private static final BigDecimal PRICE = BigDecimal.valueOf(10.99);
    private static final String DESCRIPTION = "Description book";
    private static final String COVER_IMAGE = "http://coverImage.com";
    private static final String CATEGORY_NAME = "Fiction";
    private static final String CATEGORY_DESCRIPTION = "Fictions book";
    private static final Long CATEGORY_ID = 1L;
    private static final String UPDATE_STRING = "new data string";
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Create book with valid data. Send CreatBookRequestDto return bookDto")
    public void save_ValidCreateRequestBookDto_ReturnValidBookDto() {
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setTitle(TITLE);
        bookRequestDto.setAuthor(AUTHOR);
        bookRequestDto.setIsbn(ISBN);
        bookRequestDto.setPrice(PRICE);
        bookRequestDto.setDescription(DESCRIPTION);
        bookRequestDto.setCoverImage(COVER_IMAGE);
        bookRequestDto.setCategoryIds(Set.of(CATEGORY_ID));

        Set<Category> categories = defaultCategory();

        Book book = toModel(bookRequestDto);
        book.setId(VALID_ID);
        book.setCategories(categories);

        BookDto bookDto = toDto(book);
        bookDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        when(bookMapper.toModel(bookRequestDto)).thenReturn(book);
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(categories.stream().findFirst());
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.save(bookRequestDto);

        Assertions.assertEquals(bookDto, actual);
    }

    @Test
    public void getAll_ValidPageable_ReturnAllBooks() {
        Book book = defaultBook();
        book.setId(VALID_ID);

        BookDto bookDto = toDto(book);

        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Book> bookList = List.of(book);
        Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> actual = bookService.getAll(pageable);

        Assertions.assertEquals(bookList.size(), actual.size());
    }

    @Test
    @DisplayName("Get book by valid id, return BookDto by id")
    public void getBookById_ValidBookId_ReturnValidBookDto() {
        Book book = defaultBook();
        book.setId(VALID_ID);

        BookDto bookDto = toDto(book);

        when(bookRepository.findById(VALID_ID)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.getById(VALID_ID);

        Assertions.assertEquals(book.getId(), actual.getId());
    }

    @Test
    public void getBookById_InvalidBookId_NotOk() {
        when(bookRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(INVALID_ID));
    }

    @Test
    @DisplayName("Update book by valid id, return BookDto by id")
    public void updateById_ValidBookId_ReturnUpdateBookDto() {

        Book book = defaultBook();
        book.setId(VALID_ID);

        CreateBookRequestDto updateBookRequestDto = new CreateBookRequestDto();
        updateBookRequestDto.setTitle(UPDATE_STRING + TITLE);
        updateBookRequestDto.setAuthor(UPDATE_STRING + AUTHOR);
        updateBookRequestDto.setIsbn(ISBN);
        updateBookRequestDto.setPrice(PRICE);
        updateBookRequestDto.setDescription(DESCRIPTION);
        updateBookRequestDto.setCoverImage(COVER_IMAGE);
        updateBookRequestDto.setCategoryIds(Set.of());

        Book updateBook = defaultBook();
        updateBook.setId(VALID_ID);
        updateBook.setTitle(updateBookRequestDto.getTitle());
        updateBook.setAuthor(updateBookRequestDto.getAuthor());

        BookDto updateBookDto = toDto(updateBook);

        when(bookRepository.findById(VALID_ID)).thenReturn(Optional.of(book));
        when(bookRepository.save(updateBook)).thenReturn(updateBook);
        when(bookMapper.toDto(updateBook)).thenReturn(updateBookDto);

        BookDto actual = bookService.updateById(updateBookRequestDto, VALID_ID);

        Assertions.assertEquals(VALID_ID, actual.getId());
        Assertions.assertEquals(updateBookRequestDto.getTitle(), actual.getTitle());
        Assertions.assertEquals(updateBookRequestDto.getAuthor(), updateBookDto.getAuthor());
    }

    @Test
    public void updateById_InvalidBookId_NotOk() {
        CreateBookRequestDto updateBookRequestDto = new CreateBookRequestDto();

        when(bookRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.updateById(updateBookRequestDto, INVALID_ID));
    }

    private Book defaultBook() {
        Book book = new Book();
        book.setId(VALID_ID);
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setIsbn(ISBN);
        book.setPrice(PRICE);
        book.setDescription(DESCRIPTION);
        book.setCoverImage(COVER_IMAGE);
        book.setCategories(Set.of());
        return book;
    }

    private Set<Category> defaultCategory() {
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
        return Set.of(category);
    }

    private Book toModel(CreateBookRequestDto requestDto) {
        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setPrice(requestDto.getPrice());
        book.setDescription(requestDto.getDescription());
        book.setCoverImage(requestDto.getCoverImage());
        book.setCategories(Set.of());
        return book;
    }

    private BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        bookDto.setCategoryIds(Set.of());
        return bookDto;
    }
}
