package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponse mapToProductResponse(final ProductDocument productDocument) {
        return new ProductResponse(productDocument.getId(), productDocument.getTitle(), productDocument.getDescription(),
                productDocument.getPrice(), productDocument.isDeleted());
    }

    public ProductDocument mapToProductDocument(final ProductSaveRequest productSaveRequest) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setTitle(productSaveRequest.title());
        productDocument.setDescription(productSaveRequest.description());
        productDocument.setPrice(productSaveRequest.price());
        return productDocument;
    }


    public List<ProductResponse> mapToProductResponseList(final List<ProductDocument> productDocumentList) {
        return productDocumentList.stream().map(this::mapToProductResponse).toList();
    }

    public void mapToProductDocument(final ProductSaveRequest request, final ProductDocument document) {
        document.setTitle(request.title());
        document.setDescription(request.description());
        document.setPrice(request.price());
    }
}
