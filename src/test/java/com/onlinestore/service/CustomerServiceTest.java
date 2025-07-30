package com.onlinestore.service;

import com.onlinestore.AbstractServiceLayerTest;
import com.onlinestore.dto.customer.CustomerDTO;
import com.onlinestore.dto.customer.CustomerOrderDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class CustomerServiceTest extends AbstractServiceLayerTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldGetAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertEquals(10, customers.size());
        assertEquals("Muriel Donnelly", customers.get(0).getName());
        assertEquals("Dr. Natalie Oberbrunner", customers.get(9).getName());
    }

    @Test
    void shouldSearchCustomersByName() {
        List<CustomerDTO> results = customerService.searchCustomers("Dr.");
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(c -> c.getName().equals("Dr. Winifred Morissette")));
        assertTrue(results.stream().anyMatch(c -> c.getName().equals("Dr. Natalie Oberbrunner")));
    }

    @Test
    void shouldCreateCustomer() {
        CustomerDTO newCustomer = new CustomerDTO();
        newCustomer.setName("New Customer");

        CustomerDTO createdCustomer = customerService.createCustomer(newCustomer);
        assertNotNull(createdCustomer.getId());
        assertEquals("New Customer", createdCustomer.getName());

        List<CustomerDTO> customers = customerService.getAllCustomers();
        assertEquals(11, customers.size());
    }

    @Test
    void shouldGetCustomerOrders() {
        List<CustomerOrderDTO> orders = customerService.getCustomerOrders(5L);
        assertEquals(3, orders.size());
        assertTrue(orders.stream().anyMatch(o -> o.getDescription().equals("Small Concrete Table")));
        assertTrue(orders.stream().anyMatch(o -> o.getDescription().equals("Awesome Metal Fish")));
        assertTrue(orders.stream().anyMatch(o -> o.getDescription().equals("Small Concrete Table")));
    }

    @Test
    void shouldReturnEmptyListForCustomerWithNoOrders() {
        List<CustomerOrderDTO> orders = customerService.getCustomerOrders(9L);
        assertTrue(orders.isEmpty());
    }
}
