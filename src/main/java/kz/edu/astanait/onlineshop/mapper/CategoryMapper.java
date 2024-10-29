package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ProductMapper productMapper;

    public CategoryResponse mapToCategoryResponse(final CategoryDocument categoryDocument) {
        return new CategoryResponse(categoryDocument.getName(),
                categoryDocument.getProducts().stream().map(productMapper::mapToProduct).collect(Collectors.toList()));
    }

    public CategoryDocument mapToCategoryDocument(final CategorySaveRequest categorySaveRequest) {
        CategoryDocument categoryDocument = new CategoryDocument();
        categoryDocument.setName(categorySaveRequest.name());
        return categoryDocument;
    }
}
