package hanshyn.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanshyn.onlinebookstore.dto.category.CategoryResponseDto;
import hanshyn.onlinebookstore.dto.category.CreateCategoryRequestDto;
import hanshyn.onlinebookstore.model.Category;
import java.util.List;
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
public class CategoryControllerTest {
    protected static MockMvc mockMvc;

    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "Fiction";
    private static final String CATEGORY_DESCRIPTION = "Fiction books";
    private static final int EXPECTED_TWO = 2;
    private static final int CATEGORY_ID_ONE = 1;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create category")
    @Sql(scripts = "classpath:database/controller/delete-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createCategory_ValidRequest_Success() throws Exception {
        CreateCategoryRequestDto categoryRequestDto = defaultCategoryRequestDto();

        Category category = toModel(categoryRequestDto);
        category.setId(CATEGORY_ID);

        CategoryResponseDto expected = toDto(category);

        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual,"id");
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/controller/delete-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/controller/add-two-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAll_ReturnListCategory() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryResponseDto> actual = List.of(
                objectMapper.readValue(result.getResponse().getContentAsString(),
                        CategoryResponseDto[].class));

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(EXPECTED_TWO, actual.size());
    }

    @Test
    @WithMockUser
    @DisplayName("Return category by id")
    @Sql(scripts = "classpath:database/controller/delete-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/controller/add-two-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getCategoryById_ValidId_ReturnCategoryDto() throws Exception {
        CategoryResponseDto expected = new CategoryResponseDto(
                CATEGORY_ID,
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/categories/{id}", CATEGORY_ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(CATEGORY_ID_ONE, actual.id());
        Assertions.assertEquals(expected, actual);
    }

    private CreateCategoryRequestDto defaultCategoryRequestDto() {
        return new CreateCategoryRequestDto(
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );
    }

    private CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    private Category toModel(CreateCategoryRequestDto requestDto) {
        Category category = new Category();
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());
        return category;
    }
}
