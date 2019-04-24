package com.elton.app.controller;

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
import org.springframework.http.ResponseEntity;

import com.elton.app.builder.CategoryBuilder;
import com.elton.app.domain.Category;
import com.elton.app.exception.CategoryNotFoundException;
import com.elton.app.service.CategoryService;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerUnitTest {

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	private CategoryService categoryService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private static final int HTTP_STATUS_OK = 200;

	@Test
	public void findCategorySuggestionByDescriptionTest() {
		final String description = "Test";
		final List<Category> listReturn= CategoryBuilder.getListCategoryModelPattern();
		Mockito.when(categoryService.findCategorySuggestionByDescription(description)).thenReturn(listReturn);
		final ResponseEntity<?> teste = categoryController.findCategorySuggestionByDescription(description);
		Assert.assertEquals(teste.getStatusCode().value(), HTTP_STATUS_OK);
	}

	@Test
	public void findCategorySuggestionByDescriptionWithErrorTest() {
		exception.expect(CategoryNotFoundException.class);
		final String description = "Test";
		final CategoryNotFoundException exception= new CategoryNotFoundException("Categories not found with this description: "+ description);
		Mockito.when(categoryService.findCategorySuggestionByDescription(description)).thenThrow(exception);
		categoryController.findCategorySuggestionByDescription(description);
	}
}
