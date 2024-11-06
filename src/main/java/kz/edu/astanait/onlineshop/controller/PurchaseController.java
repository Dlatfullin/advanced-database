package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.edu.astanait.onlineshop.configuration.OpenApiConfig;
import kz.edu.astanait.onlineshop.document.PurchaseDocument;
import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Purchase")
@RestController
@RequestMapping("/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Operation(summary = "Get User purchases", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseDocument> getPurchases(final @AuthenticationPrincipal AuthenticatedUser user) {
        return purchaseService.getPurchasesByUseriD(user.getId());
    }

    @Operation(summary = "Buy product", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @PostMapping("/{productId}")
    public ResponseEntity<Void> buy (final @AuthenticationPrincipal AuthenticatedUser user,
                                     @PathVariable("productId") String productId,
                                     @RequestParam(value = "amount", defaultValue = "1") BigDecimal amount) {
        purchaseService.buy(user, productId, amount);
        return ResponseEntity.ok().build();
    }
}
