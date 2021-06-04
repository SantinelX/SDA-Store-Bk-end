package com.sda.store.service;

import com.sda.store.model.Product;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Product createProduct(Product product);
    Product findById(long id);
    Product updateProduct(Product product);
    Page<Product> searchProducts(Map<String, String> params);
     void deleteProduct(long id);

     List<Product> createAll(List<Product> products);
}
