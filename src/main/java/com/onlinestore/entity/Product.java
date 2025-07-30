package com.onlinestore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "product")
@EqualsAndHashCode(
        callSuper = true,
        exclude = {"orders"})
public class Product extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private Integer quantityInStock;
    private String category;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();
}
