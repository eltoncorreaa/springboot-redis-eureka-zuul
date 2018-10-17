package  com.elton.app.service;

import java.util.List;

import com.elton.app.domain.Category;

public interface CategoryService {

	List<Category> findCategorySuggestionByDescription(String description);
}
