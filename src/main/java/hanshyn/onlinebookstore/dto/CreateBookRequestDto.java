package hanshyn.onlinebookstore.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String isbn;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    private String description;

    @NotNull
    private String coverImage;
}
