package hanshyn.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateCategoryRequestDto(
        @NotBlank
        @Length(min = 4, max = 32)
        String name,
        @NotBlank
        @Length(min = 4, max = 64)
        String description
) {
}
