package com.onlinestore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "customer")
@EqualsAndHashCode(
        callSuper = true,
        exclude = {"orders"})
public class Customer extends BaseEntity {
    private String name;

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }
}
