package expense_tracker.service;

import expense_tracker.dto.ExpenseRequestDto;
import expense_tracker.dto.ExpenseResponseDto;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequestDto dto);
    ExpenseResponseDto getExpenseById(Long id);
    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto dto);
    void deleteExpense(Long id);
    Page<ExpenseResponseDto> getPaginatedExpenses(Long userId, int page, int size, String sortBy);
    List<ExpenseResponseDto> getExpensesByDateRange(Long userId, LocalDate start, LocalDate end);
}