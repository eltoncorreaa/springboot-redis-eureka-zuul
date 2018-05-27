package  com.elton.app.service;

import java.util.List;

import com.elton.app.dto.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> findCategorySuggestionByDescription(String description);
}
