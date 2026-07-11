package expense_tracker.repository;

import expense_tracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.user.id IS NULL OR c.user.id = :userId")
    List<Category> findGlobalAndCustomByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE LOWER(c.name) = LOWER(:name) AND (c.user.id IS NULL OR c.user.id = :userId)")
    boolean existsByNameAndUserId(@Param("name") String name, @Param("userId") Long userId);
}