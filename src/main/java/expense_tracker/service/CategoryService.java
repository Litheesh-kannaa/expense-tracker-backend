package expense_tracker.service;

import expense_tracker.dto.CategoryRequestDto;
import expense_tracker.entity.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequestDto dto);
    List<Category> getCategoriesForUser(Long userId);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
}