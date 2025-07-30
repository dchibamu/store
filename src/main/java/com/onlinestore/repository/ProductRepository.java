package com.onlinestore.repository;

import com.onlinestore.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByDeletedFalse();

    Optional<Product> findByIdAndDeletedFalse(Long id);

    @Query("SELECT p FROM Product p WHERE p.id IN :ids AND p.deleted = false")
    Set<Product> findAllByIdIn(@Param("ids") Set<Long> ids);
}
