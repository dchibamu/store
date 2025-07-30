package com.onlinestore.controller;

import com.onlinestore.dto.order.OrderDTO;
import com.onlinestore.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PostMapping("/{orderId}/products/{productId}")
    public ResponseEntity<OrderDTO> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        return ResponseEntity.ok(orderService.addProductToOrder(orderId, productId));
    }

    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<OrderDTO> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        return ResponseEntity.ok(orderService.removeProductFromOrder(orderId, productId));
    }
}
