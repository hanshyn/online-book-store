package hanshyn.onlinebookstore.dto.cart;

import lombok.Data;

@Data
public class CartItemsResponseDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private int quantity;
}
