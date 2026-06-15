package expense_tracker.service;

import expense_tracker.entity.Expense;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(Expense expense);

    Expense getExpenseById(Long id);

    List<Expense> getAllExpenses();

    Expense updateExpense(Long id,
                          Expense expense);

    void deleteExpense(Long id);

    List<Expense> getExpensesByCategory(Long categoryId);

    List<Expense> getExpensesByUser(Long userId);
}
