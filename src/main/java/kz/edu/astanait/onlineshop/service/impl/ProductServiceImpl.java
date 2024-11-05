package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductByIdResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.exception.ProductDeletedException;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.mapper.ProductMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductAllResponse> getAllProducts(Pageable pageable) {
        List<ProductDocument> products = categoryRepository.findAllProducts(pageable)
                .stream()
                .filter(product -> !product.isDeleted())
                .toList();
        return productMapper.mapToProductAllResponseList(products);
    }

    @Override
    public ProductByIdResponse getProductById(String id) {
        ProductByIdResponse product = categoryRepository.findProductWithCategoryById(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        if(product.deleted()) {
            throw new ProductDeletedException("Product with id %s has been deleted".formatted(id));
        }
        return product;
    }

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

    @Override
    public ProductDocument updateProduct(String productId, ProductSaveRequest productSaveRequest) {
        String categoryId = productSaveRequest.categoryId();
        ProductDocument productDocument = categoryRepository.findProductById(productId)
                .orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(productId));
        if(productDocument.isDeleted())
            throw new ProductDeletedException("Product with id %s has been deleted".formatted(productId));
        categoryRepository.removeProductFromCategory(categoryId, productDocument);
        categoryRepository.addProductToCategory(categoryId, productId,
                productSaveRequest.title(),
                productSaveRequest.description(),
                productSaveRequest.price(),
                productSaveRequest.quantity());
        return categoryRepository.findProductById(productId)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(productId));
    }

    @Override
    public void deleteProduct(String productId) {
        categoryRepository.markProductAsDeleted(productId);
    }

    @Override
    public List<ProductAllResponse> searchProducts(String text) {
        List<ProductDocument> products = categoryRepository.findAllBy(text);
        return productMapper.mapToProductAllResponseList(products);
    }
}
