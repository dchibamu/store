package com.onlinestore.service;

import com.onlinestore.dto.product.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    List<Long> getOrdersForProduct(Long productId);
}
