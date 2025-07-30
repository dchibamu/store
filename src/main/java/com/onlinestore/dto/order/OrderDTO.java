package com.onlinestore.dto.order;

import com.onlinestore.dto.product.ProductDTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String description;
    private OrderCustomerDTO customer;
    private List<ProductDTO> products;
}
