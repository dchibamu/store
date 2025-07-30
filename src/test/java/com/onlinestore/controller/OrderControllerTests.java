package com.onlinestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinestore.dto.order.OrderCustomerDTO;
import com.onlinestore.dto.order.OrderDTO;
import com.onlinestore.entity.Customer;
import com.onlinestore.entity.Order;
import com.onlinestore.mapper.OrderMapper;
import com.onlinestore.repository.CustomerRepository;
import com.onlinestore.repository.OrderRepository;
import com.onlinestore.service.CustomerService;
import com.onlinestore.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ComponentScan(basePackageClasses = OrderMapper.class)
@RequiredArgsConstructor
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private CustomerRepository customerRepository;

    @MockitoBean
    private CustomerService customerService;

    @MockitoBean
    private OrderService orderService;

    private Order order;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setId(1L);

        order = new Order();
        order.setDescription("Test Order");
        order.setId(1L);
        order.setCustomer(customer);
    }

    @Test
    void testCreateOrder() throws Exception {

        OrderCustomerDTO customerDTO = new OrderCustomerDTO();
        customerDTO.setId(1L);

        OrderDTO newOrder = new OrderDTO();
        newOrder.setId(1L);
        newOrder.setDescription("Handcrafted Soft Chair");
        newOrder.setCustomer(customerDTO);

        newOrder.setCustomer(customerDTO);

        given(orderService.createOrder(newOrder)).willReturn(newOrder);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Handcrafted Soft Chair"))
                .andExpect(jsonPath("$.customer.id").value(1));
    }

    @Test
    void testGetOrder() throws Exception {
        OrderCustomerDTO customerDTO = new OrderCustomerDTO();
        String customerName = "Denise Harris";
        customerDTO.setId(1L);
        customerDTO.setName(customerName);

        Long id = 3L;
        String description = "Handmade Frozen Salad";
        OrderDTO order = new OrderDTO();
        order.setId(id);
        order.setDescription(description);
        order.setCustomer(customerDTO);

        given(orderService.getOrderById(id)).willReturn(order);

        mockMvc.perform(get("/order/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.customer.name").value(customerName));
    }
}
