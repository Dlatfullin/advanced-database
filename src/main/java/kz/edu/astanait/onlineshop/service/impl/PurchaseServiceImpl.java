package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.document.PurchaseDocument;
import kz.edu.astanait.onlineshop.document.UserDocument;
import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.exception.InsufficientFundsException;
import kz.edu.astanait.onlineshop.exception.InsufficientProductQuantityException;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.mapper.PurchaseMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.repository.UserRepository;
import kz.edu.astanait.onlineshop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public List<PurchaseDocument> getPurchasesByUseriD(String id) {
        return userRepository.findAllPurchasesByUserId(id);
    }

    @Override
    public void buy(AuthenticatedUser authenticatedUser, String productId, BigDecimal amount) {
        UserDocument user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CategoryDocument category = categoryRepository.findByProductId(productId)
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(productId));
        ProductDocument productDocument = category.getProducts()
                .stream()
                .filter(product-> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(productId));
        BigDecimal totalCost = productDocument.getPrice().multiply(amount);
        if(user.getBalance().compareTo(totalCost) < 0) {
            throw new InsufficientFundsException("There are not enough funds to perform the operation.");
        }
        if(productDocument.getQuantity().compareTo(amount) < 0) {
            throw new InsufficientProductQuantityException("Requested quantity exceeds available stock for product ID: %s".formatted(productId));
        }
        user.setBalance(user.getBalance().subtract(totalCost));
        productDocument.setQuantity(productDocument.getQuantity().subtract(amount));
        PurchaseDocument purchaseDocument = purchaseMapper.mapToPurchaseDocument(productDocument, amount);
        user.getPurchases().add(purchaseDocument);
        userRepository.save(user);
        categoryRepository.save(category);
    }
}
