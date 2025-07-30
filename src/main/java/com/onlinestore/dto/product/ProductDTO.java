package com.onlinestore.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private String sku;
    private BigDecimal price;
    private Integer quantityInStock;
    private String category;
    private List<Long> orderIds;
}
