package expense_tracker.service.impl;

import expense_tracker.dto.CategoryRequestDto;
import expense_tracker.entity.Category;
import expense_tracker.entity.User;
import expense_tracker.exception.ResourceNotFoundException;
import expense_tracker.repository.CategoryRepository;
import expense_tracker.repository.UserRepository;
import expense_tracker.service.CategoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Category createCategory(CategoryRequestDto dto) {
        Long userId = dto.getUserId();
        User user = null;

        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        }

        if (categoryRepository.existsByNameAndUserId(dto.getName(), userId)) {
            throw new IllegalArgumentException("Category '" + dto.getName() + "' already exists.");
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setUser(user);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategoriesForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return categoryRepository.findGlobalAndCustomByUserId(userId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}