package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.document.PurchaseDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PurchaseMapper {

    public PurchaseDocument mapToPurchaseDocument(ProductDocument productDocument, BigDecimal amount) {
        PurchaseDocument purchaseDocument = new PurchaseDocument();
        purchaseDocument.setId(ObjectId.get().toString());
        purchaseDocument.setDate(LocalDateTime.now());
        purchaseDocument.setQuantity(amount);
        purchaseDocument.setProduct(productDocument);
        return purchaseDocument;
    }
}
