package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductByIdResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.mapper.ProductMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductAllResponse> getAllProducts(Pageable pageable) {
        List<ProductDocument> products = categoryRepository.findAllProducts(pageable);
        return productMapper.mapToProductAllResponseList(products);
    }

    @Override
    public ProductByIdResponse getProductById(String id) {
        ProductDocument product = categoryRepository.findProductById(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        return productMapper.mapToProductByIdResponse(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ProductDocument createProduct(ProductSaveRequest productSaveRequest) {
        String categoryId = productSaveRequest.categoryId();
        CategoryDocument category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(categoryId));
        ProductDocument productDocument = productMapper.mapToProductDocument(productSaveRequest);
        category.getProducts().add(productDocument);
        categoryRepository.save(category);
        return productDocument;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ProductDocument updateProduct(String id, ProductSaveRequest productSaveRequest) {
        String categoryId = productSaveRequest.categoryId();
        CategoryDocument category = categoryRepository.findByProductId(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        ProductDocument productDocument = category.getProducts()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        if(!category.getId().equals(categoryId)) {
            category.getProducts().remove(productDocument);
            productDocument = createProduct(productSaveRequest);
        }
        productMapper.mapToProductDocument(productSaveRequest, productDocument);
        categoryRepository.save(category);
        return productDocument;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteProduct(String id) {
        CategoryDocument category = categoryRepository.findByProductId(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        ProductDocument productDocument = category.getProducts()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        productDocument.setDeleted(true);
        categoryRepository.save(category);
    }
}
