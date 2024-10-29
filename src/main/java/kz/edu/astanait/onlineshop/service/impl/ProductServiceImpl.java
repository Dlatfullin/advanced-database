package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.Product;
import kz.edu.astanait.onlineshop.exception.ProductNotFoundException;
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
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::mapToProduct).collect(Collectors.toList());
    }

    @Override
    public Product getProductById(String id) {
        return productMapper.mapToProduct(productRepository.findById(id).orElseThrow(ProductNotFoundException::new));
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(productMapper.mapToProductDocument(product));
    }

    @Override
    public void updateProduct(String id, Product product) {
        ProductDocument productDocument = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        ProductDocument updatedProductDocument = productMapper.mapToProductDocument(product);

        productDocument.setTitle(updatedProductDocument.getTitle());
        productDocument.setDescription(updatedProductDocument.getDescription());
        productDocument.setPrice(updatedProductDocument.getPrice());
        productDocument.setImage(updatedProductDocument.getImage());
        productDocument.setDeleted(updatedProductDocument.getDeleted());

        productRepository.save(productDocument);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
