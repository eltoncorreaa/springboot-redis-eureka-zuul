package  com.elton.app.repository.redis;

import java.util.List;

import com.elton.app.model.Category;

public interface CategoryRepositoryRedis {

	List<Category> findCategorySuggestionByDescription(String description);
}
