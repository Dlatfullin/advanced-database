package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductByIdResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<ProductAllResponse> searchProducts(Pageable pageable);

    List<ProductAllResponse> searchProducts(String query, Pageable pageable);

    ProductByIdResponse getProductById(String id);

    ProductDocument createProduct(ProductSaveRequest productSaveRequest);

    ProductDocument updateProduct(String id, ProductSaveRequest productSaveRequest);

    void deleteProduct(String id);
}
