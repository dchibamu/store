package com.onlinestore.service.impl;

import com.onlinestore.dto.order.OrderDTO;
import com.onlinestore.dto.product.ProductDTO;
import com.onlinestore.entity.Customer;
import com.onlinestore.entity.Order;
import com.onlinestore.entity.Product;
import com.onlinestore.mapper.OrderMapper;
import com.onlinestore.repository.CustomerRepository;
import com.onlinestore.repository.OrderRepository;
import com.onlinestore.repository.ProductRepository;
import com.onlinestore.service.OrderService;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAllByDeletedFalse().stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find order entity with id: " + id));
        return mapToOrderDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setDescription(orderDTO.getDescription());
        order.setDeleted(false);

        Customer customer = customerRepository
                .findByIdAndDeletedFalse(orderDTO.getCustomer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find customer entity with id: "
                        + orderDTO.getCustomer().getId()));
        order.setCustomer(customer);

        if (orderDTO.getProducts() != null) {
            Set<Product> products = productRepository.findAllByIdIn(
                    orderDTO.getProducts().stream().map(ProductDTO::getId).collect(Collectors.toSet()));
            order.setProducts(products);
        }

        return mapToOrderDTO(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO addProductToOrder(Long orderId, Long productId) {
        Order order = orderRepository
                .findByIdAndDeletedFalse(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find order entity with id: " + orderId));

        Product product = productRepository
                .findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find order entity with id: " + orderId));

        order.getProducts().add(product);
        return mapToOrderDTO(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO removeProductFromOrder(Long orderId, Long productId) {
        Order order = orderRepository
                .findByIdAndDeletedFalse(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find order entity with id: " + orderId));

        order.getProducts().removeIf(p -> p.getId().equals(productId));
        return mapToOrderDTO(orderRepository.save(order));
    }

    private OrderDTO mapToOrderDTO(Order order) {
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
        orderDTO.setCustomer(orderMapper.orderToOrderCustomerDTO(order.getCustomer()));

        orderDTO.setProducts(order.getProducts().stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setDescription(product.getDescription());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setSku(product.getSku());
                    return productDTO;
                })
                .collect(Collectors.toList()));

        return orderDTO;
    }
}
