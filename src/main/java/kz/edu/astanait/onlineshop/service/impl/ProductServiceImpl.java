package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.mapper.ProductMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.repository.ProductRepository;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductDocument> products = productRepository.findAll();
        return productMapper.mapToProductResponseList(products);
    }

    @Override
    public ProductResponse getProductById(String id) {
        return productRepository.findById(id)
                .map(productMapper::mapToProductResponse)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
    }

    @Override
    public void createProduct(ProductSaveRequest productSaveRequest) {
        String categoryId = productSaveRequest.categoryId();
        CategoryDocument category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(productSaveRequest.categoryId()));

        ProductDocument productDocument = productMapper.mapToProductDocument(productSaveRequest);
        ProductDocument product = productRepository.save(productDocument);

        category.getProducts().add(product);
        categoryRepository.save(category);
    }

    @Override
    public void updateProduct(String id, ProductSaveRequest productSaveRequest) {
        ProductDocument productDocument = productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));

        productMapper.mapToProductDocument(productSaveRequest, productDocument);

        productRepository.save(productDocument);
    }

    @Override
    public void deleteProduct(String id) {
        ProductDocument productDocument = productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));

        productDocument.setDeleted(true);

        productRepository.save(productDocument);
    }
}
