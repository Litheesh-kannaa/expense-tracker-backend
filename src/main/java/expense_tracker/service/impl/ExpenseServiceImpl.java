package expense_tracker.service.impl;

import expense_tracker.dto.ExpenseRequestDto;
import expense_tracker.dto.ExpenseResponseDto;
import expense_tracker.entity.Category;
import expense_tracker.entity.Expense;
import expense_tracker.entity.User;
import expense_tracker.exception.ResourceNotFoundException;
import expense_tracker.repository.CategoryRepository;
import expense_tracker.repository.ExpenseRepository;
import expense_tracker.repository.UserRepository;
import expense_tracker.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Expense expense = new Expense();
        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setDescription(dto.getDescription());
        expense.setUser(user);
        expense.setCategory(category);

        return mapToResponseDto(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return mapToResponseDto(expense);
    }

    @Override
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto dto) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        existingExpense.setTitle(dto.getTitle());
        existingExpense.setAmount(dto.getAmount());
        existingExpense.setExpenseDate(dto.getExpenseDate());
        existingExpense.setDescription(dto.getDescription());
        existingExpense.setUser(user);
        existingExpense.setCategory(category);

        return mapToResponseDto(expenseRepository.save(existingExpense));
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }

    @Override
    public Page<ExpenseResponseDto> getPaginatedExpenses(Long userId, int page, int size, String sortBy) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return expenseRepository.findByUserId(userId, pageable).map(this::mapToResponseDto);
    }

    @Override
    public List<ExpenseResponseDto> getExpensesByDateRange(Long userId, LocalDate start, LocalDate end) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return expenseRepository.findByUserIdAndExpenseDateBetween(userId, start, end)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private ExpenseResponseDto mapToResponseDto(Expense expense) {
        ExpenseResponseDto dto = new ExpenseResponseDto();
        dto.setId(expense.getId());
        dto.setTitle(expense.getTitle());
        dto.setAmount(expense.getAmount());
        dto.setExpenseDate(expense.getExpenseDate());
        dto.setDescription(expense.getDescription());
        dto.setUserId(expense.getUser().getId());
        dto.setUserName(expense.getUser().getName());
        dto.setCategoryId(expense.getCategory().getId());
        dto.setCategoryName(expense.getCategory().getName());
        return dto;
    }
}