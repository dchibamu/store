package com.onlinestore.service;

import com.onlinestore.dto.customer.CustomerDTO;
import com.onlinestore.dto.customer.CustomerOrderDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    List<CustomerDTO> searchCustomers(String query);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    List<CustomerOrderDTO> getCustomerOrders(Long customerId);

    void deleteCustomer(Long id, String deletedBy);
}
