package hanshyn.onlinebookstore.controller;

import hanshyn.onlinebookstore.dto.book.BookDto;
import hanshyn.onlinebookstore.dto.category.CategoryResponseDto;
import hanshyn.onlinebookstore.dto.category.CreateCategoryRequestDto;
import hanshyn.onlinebookstore.service.book.BookService;
import hanshyn.onlinebookstore.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Categories", description = "Endpoints for category")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "Get all categories", description = "Get all categories by store")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @Operation(summary = "Create categories", description = "Add new category by store")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @Operation(summary = "Get category by id", description = "Get category by id in the store")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Update category", description = "Update category by id in the store")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public CategoryResponseDto updateCategory(@PathVariable Long id,
                                              @RequestBody CreateCategoryRequestDto requestDto) {
        return categoryService.updateById(requestDto, id);
    }

    @Operation(summary = "Remove category", description = "Remove category by id in the store")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deletedById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @Operation(summary = "Get books by category id", description = "Get books by category id")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}/books")
    public List<BookDto> getBooksByCategoryId(@PathVariable Long id, Pageable pageable) {
        return bookService.findAllByCategoryId(id, pageable);
    }
}

