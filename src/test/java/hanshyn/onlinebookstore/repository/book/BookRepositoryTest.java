package hanshyn.onlinebookstore.repository.book;

import hanshyn.onlinebookstore.model.Book;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:database/book/delete-all-table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/book/add-books-and-categories-to-db.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    private static final Long CATEGORY_ID_ONE = 1L;
    private static final int EXPECTED_ONE = 1;
    private static final Long CATEGORY_ID_TWO = 2L;
    private static final int EXPECTED_TWO = 2;
    private static final Long CATEGORY_ID_INVALID = 10L;
    private static final int EXPECTED_NULL = 0;
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find all books by id category: result one book")
    public void findBooksByCategoryId_ValidId_ReturnOneBook() {
        List<Book> actual = bookRepository.findBooksByCategoryId(
                CATEGORY_ID_ONE,
                PageRequest.of(PAGE_NUMBER, PAGE_SIZE));

        Assertions.assertEquals(EXPECTED_ONE, actual.size());
    }

    @Test
    @DisplayName("Find all books by id category: result two books")
    public void findBooksByCategoryId_ValidId_ReturnTwoBook() {
        List<Book> actual = bookRepository.findBooksByCategoryId(
                CATEGORY_ID_TWO,
                PageRequest.of(PAGE_NUMBER, PAGE_SIZE));

        Assertions.assertEquals(EXPECTED_TWO, actual.size());
    }

    @Test
    @DisplayName("Find all books by id category: result null books")
    public void findBooksByCategoryId_InvalidId_ReturnNullBook() {
        List<Book> actual = bookRepository.findBooksByCategoryId(
                CATEGORY_ID_INVALID,
                PageRequest.of(PAGE_NUMBER, PAGE_SIZE));

        Assertions.assertEquals(EXPECTED_NULL, actual.size());
    }
}
