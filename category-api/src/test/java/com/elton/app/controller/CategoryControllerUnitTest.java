package com.elton.app.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.elton.app.model.Category;
import com.elton.app.objectfactory.CategoryMother;
import com.elton.app.service.CategoryService;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerUnitTest {

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	private CategoryService categoryService;

	private static final int HTTP_STATUS_OK = 200;

	@Test
	public void findCategorySuggestionByDescriptionTest() {
		final String description = "Test";
		final List<Category> listReturn= CategoryMother.getListCategoryModelPattern();
		Mockito.when(categoryService.findCategorySuggestionByDescription(description)).thenReturn(listReturn);
		final ResponseEntity<?> teste = categoryController.findCategorySuggestionByDescription(description);
		Assert.assertEquals(teste.getStatusCode().value(), HTTP_STATUS_OK);
	}
}
