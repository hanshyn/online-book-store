package hanshyn.onlinebookstore.service.category;

import hanshyn.onlinebookstore.dto.category.CategoryResponseDto;
import hanshyn.onlinebookstore.dto.category.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    CategoryResponseDto updateById(CreateCategoryRequestDto requestDto, Long id);

    void deleteById(Long id);
}
