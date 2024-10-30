package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.edu.astanait.onlineshop.domain.ProductResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all the products")
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product")
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Add product")
    @PostMapping
    public ResponseEntity<ProductSaveRequest> createProduct(@RequestBody ProductSaveRequest productSaveRequest) {
        productService.createProduct(productSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productSaveRequest);
    }

    @Operation(summary = "Edit product")
    @PatchMapping("/{id}")
    public ResponseEntity<ProductSaveRequest> updateProduct(@PathVariable String id, @RequestBody ProductSaveRequest productSaveRequest) {
        productService.updateProduct(id, productSaveRequest);
        return ResponseEntity.ok(productSaveRequest);
    }

    @Operation(summary = "Mark product as deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
