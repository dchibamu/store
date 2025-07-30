package com.onlinestore.service;

import com.onlinestore.AbstractServiceLayerTest;
import com.onlinestore.dto.order.OrderCustomerDTO;
import com.onlinestore.dto.order.OrderDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class OrderServiceTest extends AbstractServiceLayerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    void testDataLoadedCorrectly() {
        long customerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customer", Long.class);
        assertEquals(10, customerCount);

        long orderCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM \"order\"", Long.class);
        assertTrue(orderCount >= 5);
    }

    @Test
    void shouldGetAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();

        assertEquals(8, orders.size());
        assertEquals(1L, orders.get(0).getCustomer().getId());
        assertEquals("Muriel Donnelly", orders.get(0).getCustomer().getName());
    }

    @Test
    void shouldGetOrderById() {
        OrderDTO order = orderService.getOrderById(1L);

        assertNotNull(order);
        assertEquals("Handcrafted Soft Chair", order.getDescription());
        OrderCustomerDTO customer = order.getCustomer();
        assertEquals(1L, customer.getId());
        assertEquals("Muriel Donnelly", customer.getName());
    }

    @Test
    void shouldCreateOrder() {
        String courseTitle = "New Cybersecurity Course";
        OrderDTO courseOrder = new OrderDTO();
        courseOrder.setDescription(courseTitle);
        OrderCustomerDTO customerDTO = new OrderCustomerDTO();
        customerDTO.setId(1L);
        courseOrder.setCustomer(customerDTO);
        OrderDTO createdOrder = orderService.createOrder(courseOrder);

        assertNotNull(createdOrder.getId());
        assertEquals(courseTitle, createdOrder.getDescription());
        assertEquals(1L, createdOrder.getCustomer().getId());
    }
}
