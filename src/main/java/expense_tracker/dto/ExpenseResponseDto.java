package expense_tracker.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ExpenseResponseDto {
    private Long id;
    private String title;
    private Double amount;
    private LocalDate expenseDate;
    private String description;
    private Long userId;
    private String userName;
    private Long categoryId;
    private String categoryName;
}