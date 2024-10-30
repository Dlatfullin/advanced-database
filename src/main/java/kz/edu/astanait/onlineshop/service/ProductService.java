package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.domain.ProductResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String id);

    void createProduct(ProductSaveRequest productSaveRequest);

    void updateProduct(String id, ProductSaveRequest productSaveRequest);

    void deleteProduct(String id);
}
