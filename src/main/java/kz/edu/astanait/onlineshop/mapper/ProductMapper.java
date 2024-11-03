package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductByIdResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductByIdResponse mapToProductByIdResponse(final ProductDocument productDocument, final CategoryDocument categoryDocument) {
        return new ProductByIdResponse(productDocument.getId(), categoryMapper.mapToCategoryResponse(categoryDocument), productDocument.getTitle(),
                productDocument.getDescription(), productDocument.getPrice(), productDocument.getQuantity(),
                productDocument.isDeleted(), "/v1/products/" + productDocument.getId() + "/images");
    }

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
        return productDocument;
    }

    public List<ProductAllResponse> mapToProductAllResponseList(final List<ProductDocument> productDocumentList) {
        return productDocumentList.stream().map(this::mapToProductAllResponse).toList();
    }

    public void mapToProductDocument(final ProductSaveRequest request, final ProductDocument document) {
        document.setTitle(request.title());
        document.setDescription(request.description());
        document.setPrice(request.price());
        document.setQuantity(request.quantity());
    }
}
