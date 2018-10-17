package  com.elton.app.repository.redis;

import java.util.Optional;

import com.elton.app.domain.Category;

public interface CategoryRepositoryRedis {

	Category insert(Category category);

	Optional<Category> findByDescriptionEqualsIgnoreCase(String description);
}
