package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductAllResponse mapToProductAllResponse(final ProductDocument productDocument) {
        return new ProductAllResponse(productDocument.getId(), productDocument.getTitle(), productDocument.getPrice(),
                productDocument.isDeleted(), "/v1/products/" + productDocument.getId() + "/images");
    }

    public ProductDocument mapToProductDocument(final ProductSaveRequest productSaveRequest) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(ObjectId.get().toString());
        productDocument.setTitle(productSaveRequest.title());
        productDocument.setDescription(productSaveRequest.description());
        productDocument.setPrice(productSaveRequest.price());
        productDocument.setQuantity(productSaveRequest.quantity());
        productDocument.setDeleted(false);
        return productDocument;
    }

    public List<ProductAllResponse> mapToProductAllResponseList(final List<ProductDocument> productDocumentList) {
        return productDocumentList.stream().map(this::mapToProductAllResponse).toList();
    }
}
