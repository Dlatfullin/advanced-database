package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all the categories")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryResponse> getCategory() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get categories by id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryResponse getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Add new category")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategorySaveRequest categorySaveRequest) {
        categoryService.createCategory(categorySaveRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Edit category by id")
    @PatchMapping(value ="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCategory(@PathVariable String id,
        @Valid @RequestBody CategorySaveRequest categorySaveRequest) {
        categoryService.updateCategory(id, categorySaveRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete category by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
