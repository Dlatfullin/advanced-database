package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductByIdResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.graph.entity.ProductNode;
import kz.edu.astanait.onlineshop.graph.repository.ProductNodeRepository;
import kz.edu.astanait.onlineshop.mapper.ProductMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProductNodeRepository productNodeRepository;

    @Override
    public List<ProductAllResponse> getAllProducts(Pageable pageable) {
        List<ProductDocument> products = categoryRepository.findAllProducts(pageable);
        return productMapper.mapToProductAllResponseList(products);
    }

    @Override
    public ProductByIdResponse getProductById(String productId, String userId) {
        ProductByIdResponse response = getProductById(productId);
        if (productNodeRepository.getViewRelationship(userId, productId).isEmpty()) {
            productNodeRepository.viewProduct(userId, productId);
        }
        return response;
    }

    @Override
    public ProductByIdResponse getProductById(String id) {
        ProductDocument product = categoryRepository.findProductById(id)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        return productMapper.mapToProductByIdResponse(product);
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

    @Override
    public void likeProduct(String productId, String userId) {
        if (!categoryRepository.existsProductById(productId)) {
            throw ResourceNotFoundException.productNotFoundById(productId);
        }
        if (productNodeRepository.getLikeRelationship(userId, productId).isEmpty()) {
            productNodeRepository.likeProduct(userId, productId);
        }
    }

    @Override
    @Transactional
    public List<ProductAllResponse> getLikedProducts(String userId, int page, int size) {
        long skip = (long) page * size;
        Set<String> likedProductIds = productNodeRepository.getLikedProductsByUser(userId, skip, size)
                                                           .stream()
                                                           .map(ProductNode::getId)
                                                           .collect(Collectors.toSet());
        List<ProductDocument> likedProducts = categoryRepository.findAllProductsByIds(likedProductIds);
        return productMapper.mapToProductAllResponseList(likedProducts);
    }

    @Override
    @Transactional
    public List<ProductAllResponse> getViewedProducts(String userId, int page, int size) {
        long skip = (long) page * size;
        Set<String> likedProductIds = productNodeRepository.getViewedProductsByUser(userId, skip, size)
                                                           .stream()
                                                           .map(ProductNode::getId)
                                                           .collect(Collectors.toSet());
        List<ProductDocument> likedProducts = categoryRepository.findAllProductsByIds(likedProductIds);
        return productMapper.mapToProductAllResponseList(likedProducts);
    }

    @Override
    @Transactional
    public List<ProductAllResponse> getRecommendedProducts(String userId, int size) {
        Set<String> likedProductIds = productNodeRepository.getRecommendedProductsForUser(userId, size)
                                                           .stream()
                                                           .map(ProductNode::getId)
                                                           .collect(Collectors.toSet());
        List<ProductDocument> likedProducts = categoryRepository.findAllProductsByIds(likedProductIds);
        return productMapper.mapToProductAllResponseList(likedProducts);
    }
}
