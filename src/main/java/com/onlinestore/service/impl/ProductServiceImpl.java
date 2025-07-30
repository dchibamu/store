package com.onlinestore.service.impl;

import com.onlinestore.dto.product.ProductDTO;
import com.onlinestore.entity.Product;
import com.onlinestore.repository.OrderRepository;
import com.onlinestore.repository.ProductRepository;
import com.onlinestore.service.ProductService;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllByDeletedFalse().stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product entity with id: " + id));
        return mapToProductDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSku(productDTO.getSku());
        product.setDeleted(false);
        return mapToProductDTO(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getOrdersForProduct(Long productId) {
        return orderRepository.findOrderIdsByProductId(productId);
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setOrderIds(orderRepository.findOrderIdsByProductId(product.getId()));
        return productDTO;
    }
}
