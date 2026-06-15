package expense_tracker.service;

import expense_tracker.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);
}
