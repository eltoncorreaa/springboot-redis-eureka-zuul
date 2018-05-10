package  com.elton.app.service;

import java.util.List;

import com.elton.app.dto.CategoryDTO;
import com.elton.app.model.Category;

public interface CategoryService {

	List<CategoryDTO> findCategorySuggestionByDescription(String description);

	Category categorizeExpenses(String description);
}
