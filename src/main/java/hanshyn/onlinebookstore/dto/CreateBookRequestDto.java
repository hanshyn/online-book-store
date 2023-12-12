package hanshyn.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    @ISBN
    private String isbn;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    @Size(min = 10, max = 200)
    private String description;

    @NotNull
    private String coverImage;
}
