package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.document.PurchaseDocument;
import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;

import java.math.BigDecimal;
import java.util.List;

public interface PurchaseService {

    List<PurchaseDocument> getPurchasesByUseriD(String id);

    void buy(AuthenticatedUser user, String productId, BigDecimal amount);
}
