package hanshyn.onlinebookstore.controller;

import hanshyn.onlinebookstore.dto.order.OrderItemResponseDto;
import hanshyn.onlinebookstore.dto.order.OrderPlaceRequestDto;
import hanshyn.onlinebookstore.dto.order.OrderResponseDto;
import hanshyn.onlinebookstore.dto.order.OrderUpdateStatusRequestDto;
import hanshyn.onlinebookstore.model.User;
import hanshyn.onlinebookstore.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders", description = "Endpoints for orders")
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create order",
            description = "setting shipping address and new create order")
    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderPlaceRequestDto orderPlaceRequestDto) {
        return orderService.createOrder(orderPlaceRequestDto);
    }

    @Operation(summary = "Get history orders",
            description = "returning all history orders user")
    @GetMapping
    public List<OrderResponseDto> getHistoryOrder(Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.getHistoryOrder(user, pageable);
    }

    @Operation(summary = "Change order status",
            description = "changes order status by id order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}")
    public OrderResponseDto updateStatusOrderById(@PathVariable Long id,
            @RequestBody OrderUpdateStatusRequestDto updateStatusRequestDto) {
        return orderService.updateStatusOrderById(id, updateStatusRequestDto);
    }

    @Operation(summary = "Get order items by orderId",
            description = "Get all items by orderId")
    @GetMapping(value = "/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderService.getOrderItemsByOrderId(orderId);
    }

    @Operation(summary = "Get item by itemId",
            description = "get item by itemId")
    @GetMapping(value = "/{orderId}/items/{itemId}")
    public OrderItemResponseDto getItemById(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.getOrderItemById(orderId, itemId);
    }
}
