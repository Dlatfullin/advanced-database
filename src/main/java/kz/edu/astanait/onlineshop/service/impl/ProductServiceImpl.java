package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.mapper.ProductMapper;
import kz.edu.astanait.onlineshop.repository.ProductRepository;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::mapToProduct).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(String id) {
        return productMapper.mapToProduct(productRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id)));
    }

    @Override
    public void updateProduct(String id, ProductSaveRequest productSaveRequest) {
        ProductDocument productDocument = productRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        ProductDocument updatedProductDocument = productMapper.mapToProductDocument(productSaveRequest);

        productDocument.setTitle(updatedProductDocument.getTitle());
        productDocument.setDescription(updatedProductDocument.getDescription());
        productDocument.setPrice(updatedProductDocument.getPrice());
        productDocument.setImage(updatedProductDocument.getImage());
        productDocument.setDeleted(updatedProductDocument.getDeleted());

        productRepository.save(productDocument);
    }

    @Override
    public void deleteProduct(String id) {
        ProductDocument productDocument = productRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
        productDocument.setDeleted(true);
        productRepository.save(productDocument);
    }
}
