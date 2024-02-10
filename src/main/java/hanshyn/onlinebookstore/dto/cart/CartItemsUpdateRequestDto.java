package hanshyn.onlinebookstore.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemsUpdateRequestDto {
    @NotNull
    private int quantity;
}
