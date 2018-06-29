package  com.elton.app.service;

import java.util.List;

import com.elton.app.model.Category;

public interface CategoryService {

	List<Category> findCategorySuggestionByDescription(String description);
}
