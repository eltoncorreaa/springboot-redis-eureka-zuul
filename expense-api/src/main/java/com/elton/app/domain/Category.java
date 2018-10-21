package  com.elton.app.domain;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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

	public Category categorizeExpenses(final String description) {
		Category category = findCategoryInDatabases(description);
		if (description != null && category == null) {
			category = new Category();
			category.setDescription(description);
			final Category categoryRelationalDatabase = categoryRepository.save(category);
			return categoryRepositoryRedis.insert(categoryRelationalDatabase);
		}
		return category;
	}

	public Category findCategoryInDatabases(final String description) {
		final Optional<Category> category = categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(description);
		return category.isPresent() ? category.get() : categoryRepository.findByDescriptionEqualsIgnoreCase(description);
	}
}