package kz.edu.astanait.onlineshop.controller;

import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategory() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<CategorySaveRequest> createCategory(@RequestBody CategorySaveRequest categorySaveRequest) {
        categoryService.createCategory(categorySaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySaveRequest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategorySaveRequest> updateCategory(@PathVariable String id,
            @RequestBody CategorySaveRequest categorySaveRequest) {
        categoryService.updateCategory(id, categorySaveRequest);
        return ResponseEntity.ok(categorySaveRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
