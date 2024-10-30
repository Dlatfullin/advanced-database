package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import kz.edu.astanait.onlineshop.domain.ProductSaveRequest;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.mapper.CategoryMapper;
import kz.edu.astanait.onlineshop.mapper.ProductMapper;
import kz.edu.astanait.onlineshop.repository.CategoryRepository;
import kz.edu.astanait.onlineshop.repository.ProductRepository;
import kz.edu.astanait.onlineshop.service.CategoryService;
import kz.edu.astanait.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        return categoryMapper.mapToCategoryResponse(categoryRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(id)));
    }

    @Override
    public void createCategory(CategorySaveRequest categorySaveRequest) {
        categoryRepository.save(categoryMapper.mapToCategoryDocument(categorySaveRequest));
    }

    @Override
    public void updateCategory(String id, CategorySaveRequest categorySaveRequest) {
         CategoryDocument category = categoryRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(id));
         CategoryDocument updatedCategory = categoryMapper.mapToCategoryDocument(categorySaveRequest);

         category.setName(updatedCategory.getName());

         categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void addProducts(String categoryId, ProductSaveRequest productSaveRequest) {
        CategoryDocument category = categoryRepository.findById(categoryId).orElseThrow(() -> ResourceNotFoundException.categoryNotFoundById(categoryId));

        ProductDocument productDocument = productRepository.save(productMapper.mapToProductDocument(productSaveRequest));

        category.getProducts().add(productDocument);
        categoryRepository.save(category);
    }
}
