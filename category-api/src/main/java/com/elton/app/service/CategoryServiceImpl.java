package  com.elton.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.app.domain.Category;
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
		return new Category(categoryRepository, categoryRepositoryRedis).findCategorySuggestionByDescription(description);
	}
}