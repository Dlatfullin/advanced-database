package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.edu.astanait.onlineshop.configuration.OpenApiConfig;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.AuthenticatedUser;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.domain.ProductByIdResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Product")
@Validated
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all the products")
    @PageableAsQueryParam
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductAllResponse> getAllProducts(@Parameter(hidden = true) Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @Operation(summary = "Get product", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductByIdResponse getProductById(@PathVariable String id,
                                              @AuthenticationPrincipal AuthenticatedUser user) {
        return user == null
                ? productService.getProductById(id)
                : productService.getProductById(id, user.getId());
    }

    @Operation(summary = "Create product")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDocument> createProduct(@Valid @RequestBody ProductSaveRequest productSaveRequest) {
        var product = productService.createProduct(productSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "Edit product")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDocument> updateProduct(@PathVariable String id,
                                                         @Valid @RequestBody ProductSaveRequest productSaveRequest) {
        var product = productService.updateProduct(id, productSaveRequest);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Mark product as deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Like a product", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @PostMapping("/{id}/likes")
    public ResponseEntity<Void> likeProduct(@PathVariable String id,
                                            @AuthenticationPrincipal AuthenticatedUser user) {
        productService.likeProduct(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get liked products", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @GetMapping("/likes")
    public List<ProductAllResponse> getLikedProducts(@RequestParam int page,
                                                     @RequestParam int size,
                                                     @AuthenticationPrincipal AuthenticatedUser user) {
        return productService.getLikedProducts(user.getId(), page, size);
    }

    @Operation(summary = "Get viewed products", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @GetMapping("/views")
    public List<ProductAllResponse> getViewedProducts(@RequestParam int page,
                                                      @RequestParam int size,
                                                      @AuthenticationPrincipal AuthenticatedUser user) {
        return productService.getViewedProducts(user.getId(), page, size);
    }

    @Operation(summary = "Get recommended products", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @GetMapping("/recommendations")
    public List<ProductAllResponse> getRecommendedProducts(@RequestParam int size,
                                                           @AuthenticationPrincipal AuthenticatedUser user) {
        return productService.getRecommendedProducts(user.getId(), size);
    }
}
