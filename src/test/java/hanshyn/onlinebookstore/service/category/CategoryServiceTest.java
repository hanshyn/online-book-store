package hanshyn.onlinebookstore.service.category;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import hanshyn.onlinebookstore.dto.category.CategoryResponseDto;
import hanshyn.onlinebookstore.dto.category.CreateCategoryRequestDto;
import hanshyn.onlinebookstore.exception.EntityNotFoundException;
import hanshyn.onlinebookstore.mapper.CategoryMapper;
import hanshyn.onlinebookstore.model.Category;
import hanshyn.onlinebookstore.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
public class CategoryServiceTest {
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 100L;
    private static final String CATEGORY_NAME = "Fiction";
    private static final String CATEGORY_DESCRIPTION = "Fictions book";
    private static final String UPDATE_STRING = "new data string";
    private static final Long CATEGORY_ID = 1L;
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;
    private static final int EXPECTED_SIZE = 1;
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void findAll_ValidPageable_ReturnCategoryDtos() {
        Category category = defaultCategory();
        category.setId(CATEGORY_ID);

        CategoryResponseDto categoryDto = toDto(category);

        List<Category> categoryList = List.of(category);

        List<CategoryResponseDto> categoryDtos = spy(List.of(categoryDto));

        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<Category> categoryPage = new PageImpl<>(categoryList, pageable, categoryList.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        List<CategoryResponseDto> actual = categoryService.findAll(pageable);

        Assertions.assertEquals(categoryDtos.size(), actual.size());
        Assertions.assertEquals(EXPECTED_SIZE, categoryDtos.size());
    }

    @Test
    public void getById_ValidCategoryId_ReturnCategoryDto() {
        Category category = defaultCategory();
        category.setId(CATEGORY_ID);

        CategoryResponseDto categoryDto = toDto(category);

        when(categoryRepository.findById(VALID_ID)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryResponseDto actual = categoryService.getById(VALID_ID);

        Assertions.assertEquals(category.getId(), actual.id());
    }

    @Test
    public void getById_InvalidCategoryId_NotOk() {
        when(categoryRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(INVALID_ID));
    }

    @Test
    public void createCategory_ValidCategoryResponse_ReturnCategoryDto() {
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto(
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );

        Category category = new Category();
        category.setId(VALID_ID);
        category.setName(categoryRequestDto.name());
        category.setDescription(categoryRequestDto.description());

        CategoryResponseDto categoryResponseDto = toDto(category);

        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);

        CategoryResponseDto actual = categoryService.save(categoryRequestDto);

        Assertions.assertEquals(categoryResponseDto, actual);
    }

    @Test
    public void updateById_ValidCategoryId_ReturnUpdateCategoryDto() {
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto(
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );

        Category category = defaultCategory();
        category.setId(CATEGORY_ID);

        Category updateCategory = category;
        updateCategory.setName(categoryRequestDto.name());
        updateCategory.setDescription(categoryRequestDto.description());

        CategoryResponseDto categoryResponseDto = toDto(updateCategory);

        when(categoryRepository.findById(VALID_ID)).thenReturn(Optional.of(category));
        when(categoryRepository.save(updateCategory)).thenReturn(updateCategory);
        when(categoryMapper.toDto(updateCategory)).thenReturn(categoryResponseDto);

        CategoryResponseDto actual = categoryService.updateById(categoryRequestDto, VALID_ID);

        Assertions.assertEquals(categoryResponseDto.id(), actual.id());
        Assertions.assertEquals(categoryResponseDto.name(), actual.name());
    }

    @Test
    public void updateByID_InvalidId_NotOk() {
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto(
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );

        when(categoryRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.updateById(categoryRequestDto, INVALID_ID));
    }

    private Category defaultCategory() {
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
        return category;
    }

    private CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
