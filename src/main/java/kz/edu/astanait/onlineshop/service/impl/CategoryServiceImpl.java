package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.mapper.CategoryMapper;
import kz.edu.astanait.onlineshop.mapper.ProductMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.repository.ProductRepository;
import kz.edu.astanait.onlineshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<CategoryDocument> categories = categoryRepository.findAll();
        return categoryMapper.mapToCategoryResponseList(categories);
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapToCategoryResponse)
                .orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(id));
    }

    @Override
    public void createCategory(CategorySaveRequest categorySaveRequest) {
        var category = categoryMapper.mapToCategoryDocument(categorySaveRequest);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(String id, CategorySaveRequest categorySaveRequest) {
         CategoryDocument category = categoryRepository.findById(id)
                 .orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(id));

         categoryMapper.mapToCategoryDocument(categorySaveRequest, category);

         categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
