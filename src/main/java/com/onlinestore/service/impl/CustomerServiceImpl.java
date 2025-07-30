package com.onlinestore.service.impl;

import com.onlinestore.dto.customer.CustomerDTO;
import com.onlinestore.dto.customer.CustomerOrderDTO;
import com.onlinestore.entity.Customer;
import com.onlinestore.mapper.CustomerMapper;
import com.onlinestore.repository.CustomerRepository;
import com.onlinestore.repository.OrderRepository;
import com.onlinestore.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerMapper.customersToCustomerDTOs(customerRepository.findAllByDeletedFalse());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find customer entity with id: " + id));
        return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> searchCustomers(String query) {
        List<Customer> customers = customerRepository.findByNameContainingIgnoreCaseAndDeletedFalse(query);
        return customerMapper.customersToCustomerDTOs(customers);
    }

    @Override
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setDeleted(false);
        return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id, String deletedBy) {
        Customer customer = customerRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find customer entity with id: " + id));

        customer.setDeleted(true);
        customer.setDeletedBy(deletedBy);
        customer.setDeletedAt(Instant.now());
        customerRepository.save(customer);

        orderRepository.softDeleteByCustomerId(id, deletedBy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerOrderDTO> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerIdAndDeletedFalse(customerId).stream()
                .map(order -> {
                    CustomerOrderDTO dto = new CustomerOrderDTO();
                    dto.setId(order.getId());
                    dto.setDescription(order.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
