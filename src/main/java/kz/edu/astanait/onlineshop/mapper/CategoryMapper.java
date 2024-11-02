package kz.edu.astanait.onlineshop.mapper;

import kz.edu.astanait.onlineshop.document.CategoryDocument;
import kz.edu.astanait.onlineshop.domain.CategorySaveRequest;
import kz.edu.astanait.onlineshop.domain.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public CategoryResponse mapToCategoryResponse(final CategoryDocument categoryDocument) {
        return new CategoryResponse(categoryDocument.getId(), categoryDocument.getName());
    }

    public CategoryDocument mapToCategoryDocument(final CategorySaveRequest categorySaveRequest) {
        CategoryDocument categoryDocument = new CategoryDocument();
        categoryDocument.setName(categorySaveRequest.name());
        return categoryDocument;
    }

    public void mapToCategoryDocument(final CategorySaveRequest request, final CategoryDocument document) {
        document.setName(request.name());
    }

    public List<CategoryResponse> mapToCategoryResponseList(final List<CategoryDocument> categoryDocuments) {
        return categoryDocuments.stream()
                .map(this::mapToCategoryResponse)
                .toList();
    }
}
