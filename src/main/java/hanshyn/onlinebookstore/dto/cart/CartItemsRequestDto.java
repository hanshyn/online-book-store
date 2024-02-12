package hanshyn.onlinebookstore.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemsRequestDto {
    @NotNull
    private Long bookId;

    @NotNull
    private int quantity;
}
