package hanshyn.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanshyn.onlinebookstore.dto.book.BookDto;
import hanshyn.onlinebookstore.dto.book.CreateBookRequestDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;

    private static final Long VALID_ID = 1L;
    private static final Long BOOK_ID = 1L;
    private static final String TITLE = "Sample Book 1";
    private static final String AUTHOR = "Author a";
    private static final String ISBN = "9780545010009";
    private static final BigDecimal PRICE = BigDecimal.valueOf(10);
    private static final String DESCRIPTION = "Yet another sample book description.";
    private static final String COVER_IMAGE = "http://example.com/cover3.jpg";
    private static final int EXPECTED_THREE_BOOKS = 3;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create  new book")
    @Sql(scripts = "classpath:database/controller/delete-all-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createBook_ValidRequest_Success() throws Exception {
        CreateBookRequestDto bookRequestDto = defaultBookRequestDto();

        BookDto expected = convertBookRequestDtoToBookDto(bookRequestDto);

        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        EqualsBuilder.reflectionEquals(expected, actual,"id");
    }

    @Test
    @WithMockUser
    @DisplayName("Get all books")
    @Sql(scripts = "classpath:database/controller/delete-all-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/controller/add-three-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAll_ReturnListBooks() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = List.of(
                objectMapper.readValue(result.getResponse().getContentAsString(),
                        BookDto[].class));

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(EXPECTED_THREE_BOOKS, actual.size());
    }

    @Test
    @WithMockUser
    @DisplayName("Return book by id")
    @Sql(scripts = "classpath:database/controller/delete-all-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/controller/add-three-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getBookById_ValidId_ReturnBookDto() throws Exception {
        BookDto expected = defaultBookDto();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/{id}", VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(VALID_ID, actual.getId());
        Assertions.assertEquals(expected, actual);
    }

    private CreateBookRequestDto defaultBookRequestDto() {
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        bookRequestDto.setTitle(TITLE);
        bookRequestDto.setAuthor(AUTHOR);
        bookRequestDto.setIsbn(ISBN);
        bookRequestDto.setPrice(PRICE);
        bookRequestDto.setDescription(DESCRIPTION);
        bookRequestDto.setCoverImage(COVER_IMAGE);
        bookRequestDto.setCategoryIds(Set.of());
        return bookRequestDto;
    }

    private BookDto convertBookRequestDtoToBookDto(CreateBookRequestDto bookRequestDto) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(bookRequestDto.getTitle());
        bookDto.setAuthor(bookRequestDto.getAuthor());
        bookDto.setIsbn(bookRequestDto.getIsbn());
        bookDto.setPrice(bookRequestDto.getPrice());
        bookDto.setDescription(bookRequestDto.getDescription());
        bookDto.setCoverImage(bookRequestDto.getCoverImage());
        bookDto.setCategoryIds(bookRequestDto.getCategoryIds());
        return bookDto;
    }

    private BookDto defaultBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(BOOK_ID);
        bookDto.setTitle(TITLE);
        bookDto.setAuthor(AUTHOR);
        bookDto.setIsbn(ISBN);
        bookDto.setPrice(PRICE);
        bookDto.setDescription(DESCRIPTION);
        bookDto.setCoverImage(COVER_IMAGE);
        bookDto.setCategoryIds(Set.of());
        return bookDto;
    }
}
