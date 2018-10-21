package  com.elton.app.repository.redis;

import java.util.List;

import com.elton.app.domain.Category;

public interface CategoryRepositoryRedis {

	List<Category> findCategorySuggestionByDescription(String description);
}