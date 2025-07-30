package com.onlinestore.service;

import com.onlinestore.dto.order.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO addProductToOrder(Long orderId, Long productId);

    OrderDTO removeProductFromOrder(Long orderId, Long productId);
}
