package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.edu.astanait.onlineshop.configuration.OpenApiConfig;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.ProductAllResponse;
import kz.edu.astanait.onlineshop.service.CategoryService;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category")
@Validated
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Operation(summary = "Get all the categories")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryResponse> getCategory() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get categories by id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryResponse getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Get categories products")
    @PageableAsQueryParam
    @GetMapping(path = "/{id}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductAllResponse> getProductsByCategory(@Parameter(hidden = true) Pageable pageable,
                                                          @PathVariable("id") String id) {
        return productService.getProductsByCategory(id, pageable);
    }

    @Operation(summary = "Add new category", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategorySaveRequest categorySaveRequest) {
        var category = categoryService.createCategory(categorySaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Operation(summary = "Edit category by id", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @PutMapping(path ="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String id,
                                                           @Valid @RequestBody CategorySaveRequest categorySaveRequest) {
        var category = categoryService.updateCategory(id, categorySaveRequest);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Delete category by id", security = @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
