package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.Pagination;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductByIdResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;

import java.util.List;

public interface ProductService {

    List<ProductAllResponse> getAllProducts(Pagination pagination);

    ProductByIdResponse getProductById(String id);

    ProductDocument createProduct(ProductSaveRequest productSaveRequest);

    ProductDocument updateProduct(String id, ProductSaveRequest productSaveRequest);

    void deleteProduct(String id);
}
