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

    List<ProductAllResponse> getProductsByCategory(String categoryId, Pageable pageable);

    ProductByIdResponse getProductById(String productId, String userId);

    ProductByIdResponse getProductById(String id);

    ProductDocument createProduct(ProductSaveRequest productSaveRequest);

    ProductDocument updateProduct(String id, ProductSaveRequest productSaveRequest);

    void deleteProduct(String id);

    void likeProduct(String productId, String userId);

    List<ProductAllResponse> getLikedProducts(String userId, int page, int size);

    List<ProductAllResponse> getViewedProducts(String userId, int page, int size);

    List<ProductAllResponse> getRecommendedProducts(String userId, int size);
}
