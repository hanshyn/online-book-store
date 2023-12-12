package hanshyn.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @ISBN
    private String isbn;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotBlank
    @Size(min = 10, max = 200)
    private String description;

    @NotBlank
    private String coverImage;
}
