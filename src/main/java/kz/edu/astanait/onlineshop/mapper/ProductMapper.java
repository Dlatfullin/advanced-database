package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse mapToProduct(final ProductDocument productDocument) {
        return new ProductResponse(productDocument.getId(), productDocument.getTitle(), productDocument.getDescription(),
                productDocument.getPrice(), productDocument.getDeleted());
    }

    public ProductDocument mapToProductDocument(final ProductSaveRequest productSaveRequest) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setTitle(productSaveRequest.title());
        productDocument.setDescription(productSaveRequest.description());
        productDocument.setPrice(productSaveRequest.price());
        productDocument.setDeleted(productSaveRequest.deleted());
//        productDocument.setImage(productResponse.image());
        return productDocument;
    }
}
