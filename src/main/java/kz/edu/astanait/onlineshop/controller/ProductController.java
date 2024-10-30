package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kz.edu.astanait.onlineshop.domain.ProductResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all the products")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product")
    @GetMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Create product")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductSaveRequest productSaveRequest) {
        productService.createProduct(productSaveRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Edit product")
    @PatchMapping( value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProduct(@PathVariable String id,@Valid @RequestBody ProductSaveRequest productSaveRequest) {
        productService.updateProduct(id, productSaveRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark product as deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
