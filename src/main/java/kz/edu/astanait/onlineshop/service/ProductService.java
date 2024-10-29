package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(String id);

    void createProduct(Product product);

    void updateProduct(String id, Product product);

    void deleteProduct(String id);
}
