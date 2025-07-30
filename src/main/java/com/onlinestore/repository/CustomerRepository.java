package com.onlinestore.repository;

import com.onlinestore.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByDeletedFalse();

    Optional<Customer> findByIdAndDeletedFalse(Long id);

    List<Customer> findByNameContainingIgnoreCaseAndDeletedFalse(String query);
}
