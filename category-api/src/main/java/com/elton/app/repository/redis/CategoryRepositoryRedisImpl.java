package  com.elton.app.repository.redis;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.elton.app.model.Category;
import com.elton.app.support.RedisKeysHelper;

@Repository
public class CategoryRepositoryRedisImpl implements CategoryRepositoryRedis{

	private RedisTemplate<String, String> redisTemplate;

	@Override
	public List<Category> findCategorySuggestionByDescription(final String description) {
		final List<String> listJson= redisTemplate.opsForValue().multiGet(redisTemplate.keys("categories:*"));
		final List<Category> listCategory= listJson.stream().map(json -> RedisKeysHelper.serializableToObject(json, Category.class)).collect(Collectors.toList());
		return listCategory.stream().filter(obj -> obj.getDescription().contains(description)).collect(Collectors.toList());
	}
}
