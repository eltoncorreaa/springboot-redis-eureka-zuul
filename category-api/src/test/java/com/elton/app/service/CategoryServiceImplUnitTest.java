package com.elton.app.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.elton.app.exception.CategoryNotFoundException;
import com.elton.app.model.Category;
import com.elton.app.objectfactory.CategoryMother;
import com.elton.app.repository.CategoryRepository;
import com.elton.app.repository.redis.CategoryRepositoryRedis;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplUnitTest {

	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryRepositoryRedis categoryRepositoryRedis;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private static final String DESCRIPTION_MODEL_TEST = CategoryMother.DESCRIPTION_MODEL_TEST;

	@Test
	public void findCategorySuggestionByDescriptionTest() {
		final List<Category> listCategory= CategoryMother.getListCategoryModelPattern();

		Mockito.when(categoryRepositoryRedis.findCategorySuggestionByDescription(DESCRIPTION_MODEL_TEST)).thenReturn(new ArrayList<>());
		Mockito.when(categoryRepository.findByDescriptionContainingIgnoreCase(DESCRIPTION_MODEL_TEST)).thenReturn(listCategory);

		final List<Category> result = categoryServiceImpl.findCategorySuggestionByDescription(DESCRIPTION_MODEL_TEST);

		Assert.assertEquals(result.size(), 1);
		Assert.assertEquals(listCategory.get(0), result.get(0));
	}

	@Test
	public void findCategorySuggestionByDescriptionErrorTest() {
		exception.expect(CategoryNotFoundException.class);
		Mockito.when(categoryRepositoryRedis.findCategorySuggestionByDescription(DESCRIPTION_MODEL_TEST)).thenReturn(new ArrayList<>());
		Mockito.when(categoryRepository.findByDescriptionContainingIgnoreCase(DESCRIPTION_MODEL_TEST)).thenReturn(new ArrayList<>());

		categoryServiceImpl.findCategorySuggestionByDescription(DESCRIPTION_MODEL_TEST);
	}
}
