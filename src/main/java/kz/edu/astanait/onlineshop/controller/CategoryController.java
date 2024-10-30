package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all the categories")
    @GetMapping
    public List<CategoryResponse> getCategory() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get categories by id")
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Add new category")
    @PostMapping
    public ResponseEntity<CategorySaveRequest> createCategory(@RequestBody CategorySaveRequest categorySaveRequest) {
        categoryService.createCategory(categorySaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaveRequest);
    }

    @Operation(summary = "Add product into category")
    @PostMapping("/{id}/products")
    public ResponseEntity<HttpStatus> addProductToCategory(@PathVariable String id, @RequestBody ProductSaveRequest productSaveRequest) {
        categoryService.addProducts(id, productSaveRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Edit category by id")
    @PatchMapping("/{id}")
    public ResponseEntity<CategorySaveRequest> updateCategory(@PathVariable String id,
            @RequestBody CategorySaveRequest categorySaveRequest) {
        categoryService.updateCategory(id, categorySaveRequest);
        return ResponseEntity.ok(categorySaveRequest);
    }

    @Operation(summary = "Delete category by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
