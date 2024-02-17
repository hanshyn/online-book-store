package hanshyn.onlinebookstore.service.order;

import static hanshyn.onlinebookstore.model.OrderStatus.Status.PENDING;

import hanshyn.onlinebookstore.dto.order.OrderItemResponseDto;
import hanshyn.onlinebookstore.dto.order.OrderPlaceRequestDto;
import hanshyn.onlinebookstore.dto.order.OrderResponseDto;
import hanshyn.onlinebookstore.dto.order.OrderUpdateStatusRequestDto;
import hanshyn.onlinebookstore.exception.EntityNotFoundException;
import hanshyn.onlinebookstore.mapper.OrderItemMapper;
import hanshyn.onlinebookstore.mapper.OrderMapper;
import hanshyn.onlinebookstore.model.CartItem;
import hanshyn.onlinebookstore.model.Order;
import hanshyn.onlinebookstore.model.OrderItem;
import hanshyn.onlinebookstore.model.OrderStatus;
import hanshyn.onlinebookstore.model.ShoppingCart;
import hanshyn.onlinebookstore.model.User;
import hanshyn.onlinebookstore.repository.order.OrderItemRepository;
import hanshyn.onlinebookstore.repository.order.OrderRepository;
import hanshyn.onlinebookstore.repository.order.StatusOrderRepository;
import hanshyn.onlinebookstore.repository.shopping.CartItemRepository;
import hanshyn.onlinebookstore.repository.shopping.ShoppingCartRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final StatusOrderRepository statusOrderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public OrderResponseDto createOrder(OrderPlaceRequestDto orderPlaceRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Can't found user by usr_id: " + user.getId())
        );

        OrderStatus statusOrder = statusOrderRepository.findByStatus(PENDING);

        Order order = addNewOrder(user, shoppingCart, statusOrder, orderPlaceRequestDto);

        Set<OrderItem> orderItems = cartItemRepository.findByShoppingCartId(shoppingCart.getId())
                .stream()
                .map(cartItem -> cartItemToOrderItem(cartItem, order))
                .collect(Collectors.toSet());

        orderItemRepository.saveAll(orderItems);

        Set<OrderItemResponseDto> orderItemResponseDtos = orderItemRepository
                .findOrderItemsByOrder_Id(order.getId()).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());

        OrderResponseDto orderResponseDto = orderMapper.toDto(order);
        orderResponseDto.orderItems().addAll(orderItemResponseDtos);

        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> getHistoryOrder(User user, Pageable pageable) {
        return orderRepository.findByUserId(user.getId(), pageable).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderResponseDto updateStatusOrderById(
            Long orderId,
            OrderUpdateStatusRequestDto orderUpdateStatusRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findOrderByIdAndUserId(orderId, user.getId());

        OrderStatus statusOrder = statusOrderRepository.findByStatus(
                OrderStatus.Status.valueOf(orderUpdateStatusRequestDto.status()));

        order.setStatus(statusOrder);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return orderItemRepository.findOrderItemsByOrder_IdAndOrder_User_Id(orderId, user.getId())
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long orderId, Long itemId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        OrderItem orderItem = orderItemRepository.findOrderItemByIdAndOrder_IdAndOrder_User_Id(
                itemId,
                orderId,
                user.getId());

        return orderItemMapper.toDto(orderItem);
    }

    private Order addNewOrder(User user,
                              ShoppingCart shoppingCart,
                              OrderStatus statusOrder,
                              OrderPlaceRequestDto orderPlaceRequestDto) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(countTotal(shoppingCart));
        order.setStatus(statusOrder);
        order.setOrderItems(new HashSet<>());
        order.setShippingAddress(orderPlaceRequestDto.shippingAddress());

        orderRepository.save(order);
        return order;
    }

    private BigDecimal countTotal(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().isEmpty()
                ? BigDecimal.valueOf(0) : shoppingCart.getCartItems().stream()
                .map(cartItem ->
                        cartItem.getBook().getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    private OrderItem cartItemToOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());

        cartItemRepository.delete(cartItem);
        return orderItem;
    }
}
