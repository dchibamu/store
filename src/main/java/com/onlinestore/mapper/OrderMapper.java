package com.onlinestore.mapper;

import com.onlinestore.dto.order.OrderCustomerDTO;
import com.onlinestore.dto.order.OrderDTO;
import com.onlinestore.dto.product.ProductDTO;
import com.onlinestore.entity.Customer;
import com.onlinestore.entity.Order;
import com.onlinestore.entity.Product;

import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "products", source = "products", qualifiedByName = "mapProducts")
    OrderDTO orderToOrderDTO(Order order);

    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);

    OrderCustomerDTO orderToOrderCustomerDTO(Customer customer);

    @Named("mapProducts")
    default List<ProductDTO> mapProducts(Set<Product> products) {
        if (products == null) {
            return Collections.emptyList();
        }
        return products.stream().map(this::mapProductToDTO).collect(Collectors.toList());
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantityInStock", source = "quantityInStock")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "orderIds", ignore = true)
    ProductDTO mapProductToDTO(Product product);
}
