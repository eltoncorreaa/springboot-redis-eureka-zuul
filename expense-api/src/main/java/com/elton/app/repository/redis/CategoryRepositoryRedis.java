package  com.elton.app.repository.redis;

import java.util.Optional;

import com.elton.app.model.Category;

public interface CategoryRepositoryRedis {

	Category insert(Category category);

	Optional<Category> findByDescriptionEqualsIgnoreCase(String description);
}
