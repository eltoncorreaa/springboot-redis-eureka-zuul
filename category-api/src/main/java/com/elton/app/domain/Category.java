package  com.elton.app.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elton.app.exception.CategoryNotFoundException;
import com.elton.app.repository.CategoryRepository;
import com.elton.app.repository.redis.CategoryRepositoryRedis;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CATEGORY")
@SequenceGenerator(name = "SEQUENCE_CATEGORY", sequenceName = "SEQUENCE_CATEGORY")
@Getter @Setter @EqualsAndHashCode
public class Category implements Serializable {

	private static final long serialVersionUID = -448760385229721893L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQUENCE_CATEGORY")
	@Column(name = "ID_CATEGORY", precision = 12, scale = 0)
	private Long id;

	@Column(name = "DESCRIPTION", nullable = false, length = 200)
	private String description;

	@Transient @Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private CategoryRepository categoryRepository;
	@Transient @Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private CategoryRepositoryRedis categoryRepositoryRedis;

	public Category (){}

	public Category(final CategoryRepository categoryRepository, final CategoryRepositoryRedis categoryRepositoryRedis){
		this.categoryRepository = categoryRepository;
		this.categoryRepositoryRedis = categoryRepositoryRedis;
	}

	public List<Category> findCategorySuggestionByDescription(final String description){
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