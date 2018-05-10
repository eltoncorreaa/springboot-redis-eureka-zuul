package  com.elton.app.repository.redis;

import java.util.List;

import com.elton.app.model.Category;

public interface CategoryRepositoryRedis {

	Category insert(Category category);

	List<Category> findCategorySuggestionByDescription(String description);

	Category findByDescriptionEqualsIgnoreCase(String description);
}
