package hanshyn.onlinebookstore.dto.order;

public record OrderItemResponseDto(
        Long id,
        Long bookId,
        int quantity
) {
}
