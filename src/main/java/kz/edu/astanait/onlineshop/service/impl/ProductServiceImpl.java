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
    public List<ProductAllResponse> searchProducts(Pageable pageable) {
        List<ProductDocument> products = categoryRepository.findAllProducts(pageable);
        return productMapper.mapToProductAllResponseList(products);
    }

    @Override
    public List<ProductAllResponse> searchProducts(String query, Pageable pageable) {
        List<ProductDocument> products = categoryRepository.findAllProducts(query, pageable);
        return productMapper.mapToProductAllResponseList(products);
    }

    @Override
    public ProductByIdResponse getProductById(String id) {
        CategoryDocument category = categoryRepository.findByProductId(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        ProductDocument productDocument = category.getProducts()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        if(productDocument.isDeleted()) {
            throw new ProductDeletedException("Product with id " + id + " has been deleted");
        }
        return productMapper.mapToProductByIdResponse(productDocument, category);
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
        categoryRepository.removeProductFromCategory(categoryId, productId);
        categoryRepository.addProductToCategory(categoryId, productId,
                productSaveRequest.title(),
                productSaveRequest.description(),
                productSaveRequest.price(),
                productSaveRequest.quantity());
        return categoryRepository.findProductById(productId)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(productId));
    }

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
