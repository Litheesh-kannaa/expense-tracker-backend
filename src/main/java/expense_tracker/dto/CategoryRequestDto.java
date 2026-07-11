package expense_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private Long userId; // Optional: If provided, it's a custom category. If null, it's a global default.
}