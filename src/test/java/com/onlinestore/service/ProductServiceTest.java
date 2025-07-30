package com.onlinestore.service;

import com.onlinestore.AbstractServiceLayerTest;
import com.onlinestore.dto.product.ProductDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class ProductServiceTest extends AbstractServiceLayerTest {

    @Autowired
    private ProductService productService;

    @Test
    void shouldCreateProduct() {
        String name = "Cherished Daughter 10oz Stainless";
        String description =
                "Steel Coffee Mug - Perfect Birthday Gift from Mom, Reusable & BPA-Free, Ideal for All Seasons";
        BigDecimal unitPrice = new BigDecimal(111.98);
        String sku = "74278342AAA";
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(unitPrice);
        productDTO.setSku(sku);

        ProductDTO savedProduct = productService.createProduct(productDTO);

        assertNotNull(savedProduct.getId());
        assertEquals(name, savedProduct.getName());
        assertEquals(description, savedProduct.getDescription());
        assertEquals(unitPrice, savedProduct.getPrice());
    }

    @Test
    void shouldGetAllProducts() {
        long[] metalFishOrders = {2, 7};
        List<ProductDTO> products = productService.getAllProducts();
        assertEquals(5, products.size());
        ProductDTO productDTO = products.get(1);
        assertArrayEquals(
                metalFishOrders,
                productDTO.getOrderIds().stream().mapToLong(Long::longValue).toArray());
    }

    @Test
    void shouldGetProductById() {
        String description = "Handcrafted Soft Chair";
        String name = "Soft Chair";
        long[] orders = {1, 4};
        ProductDTO productDTO = productService.getProductById(1L);

        assertEquals(description, productDTO.getDescription());
        assertEquals(name, productDTO.getName());
        assertArrayEquals(
                orders,
                productDTO.getOrderIds().stream().mapToLong(Long::longValue).toArray());
    }
}
