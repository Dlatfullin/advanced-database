package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapToProduct(final ProductDocument productDocument) {
        return new Product(productDocument.getTitle(), productDocument.getDescription(), productDocument.getPrice(), productDocument.getDeleted(), productDocument.getImage());
    }

    public ProductDocument mapToProductDocument(final Product product) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setTitle(product.title());
        productDocument.setDescription(product.description());
        productDocument.setPrice(product.price());
        productDocument.setDeleted(product.deleted());
        productDocument.setImage(product.image());
        return productDocument;
    }
}
