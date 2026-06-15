package expense_tracker.repository;

import expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByUserId(Long userId);
}
