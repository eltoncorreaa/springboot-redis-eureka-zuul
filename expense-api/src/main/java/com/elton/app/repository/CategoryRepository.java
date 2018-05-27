package  com.elton.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elton.app.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByDescriptionEqualsIgnoreCase(String description);
}
