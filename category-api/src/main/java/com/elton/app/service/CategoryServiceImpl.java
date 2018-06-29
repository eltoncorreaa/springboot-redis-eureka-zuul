package  com.elton.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.app.exception.CategoryNotFoundException;
import com.elton.app.model.Category;
import com.elton.app.repository.CategoryRepository;
import com.elton.app.repository.redis.CategoryRepositoryRedis;

@Transactional(readOnly = true)
@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryRepositoryRedis categoryRepositoryRedis;

	@Override
	public List<Category> findCategorySuggestionByDescription(final String description) {
		List<Category> listCategories = categoryRepositoryRedis.findCategorySuggestionByDescription(description);
		if(listCategories.isEmpty()) {
			listCategories = categoryRepository.findByDescriptionContainingIgnoreCase(description);
		}
		if(listCategories.isEmpty()) {
			throw new CategoryNotFoundException("Categories not found with this description: "+ description);
		}
		return listCategories;
	}
}
