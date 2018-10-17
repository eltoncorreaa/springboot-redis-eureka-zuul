package com.elton.app.objectfactory;

import org.springframework.stereotype.Component;

import com.elton.app.domain.Category;


/**
 * Class used in test classes for the purpose of creating
 * reusable entities. View in: http://martinfowler.com/bliki/ObjectMother.html
 */
@Component
public class CategoryMother {

	public static final String DESCRIPTION_MODEL_TEST = "Description Category";

	public static Category getCategoryModelWithId() {
		final Category category = new Category();
		category.setId(1L);
		category.setDescription(DESCRIPTION_MODEL_TEST);
		return category;
	}

	public static Category getCategoryModelWithoutId() {
		final Category category = new Category();
		category.setDescription(DESCRIPTION_MODEL_TEST);
		return category;
	}
}
