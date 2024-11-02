package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import kz.edu.astanait.onlineshop.mapper.CategoryMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<CategoryDocument> categories = categoryRepository.findAll();
        return categoryMapper.mapToCategoryResponseList(categories);
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        CategoryDocument categoryDocument = categoryRepository.findByIdOrElseThrow(id);
        return categoryMapper.mapToCategoryResponse(categoryDocument);
    }

    @Override
    public CategoryResponse createCategory(CategorySaveRequest categorySaveRequest) {
        var category = categoryMapper.mapToCategoryDocument(categorySaveRequest);
        CategoryDocument categoryDocument = categoryRepository.save(category);
        return categoryMapper.mapToCategoryResponse(categoryDocument);
    }

    @Override
    public CategoryResponse updateCategory(String id, CategorySaveRequest categorySaveRequest) {
         CategoryDocument category = categoryRepository.findByIdOrElseThrow(id);
         categoryMapper.mapToCategoryDocument(categorySaveRequest, category);
         CategoryDocument categoryDocument = categoryRepository.save(category);
         return categoryMapper.mapToCategoryResponse(categoryDocument);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
