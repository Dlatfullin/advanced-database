package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(String id);

    CategoryResponse createCategory(CategorySaveRequest categorySaveRequest);

    CategoryResponse updateCategory(String id, CategorySaveRequest categorySaveRequest);

    void deleteCategory(String id);
}
