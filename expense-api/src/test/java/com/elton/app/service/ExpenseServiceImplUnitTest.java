package com.elton.app.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.elton.app.domain.Category;
import com.elton.app.domain.Expense;
import com.elton.app.exception.OptimisticLockException;
import com.elton.app.objectfactory.CategoryMother;
import com.elton.app.objectfactory.ExpenseMother;
import com.elton.app.repository.CategoryRepository;
import com.elton.app.repository.ExpenseRepository;
import com.elton.app.repository.redis.CategoryRepositoryRedis;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseServiceImplUnitTest {

	@InjectMocks
	private ExpenseServiceImpl expenseServiceImpl;

	@Mock
	private ExpenseRepository expenseRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryRepositoryRedis categoryRepositoryRedis;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void insertWithExistingCategoryInRedisTest() {
		final Expense modelWithId =ExpenseMother.getExpenseModelWithId();
		Optional<Category> optionalCategory= Optional.of(CategoryMother.getCategoryModelWithId());
		final Expense modelWithoutId = ExpenseMother.getExpenseModelWithoutId();

		Mockito.when(categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(modelWithoutId.getCategory().getDescription())).thenReturn(optionalCategory);
		Mockito.when(expenseRepository.save(modelWithoutId)).thenReturn(modelWithId);

		final Expense result = expenseServiceImpl.insert(modelWithoutId);
		Assert.assertEquals(result, modelWithId);
	}

	@Test
	public void insertWithExistingCategoryInRelationalDatabaseTest() {
		final Expense modelWithId =ExpenseMother.getExpenseModelWithId();
		final Expense modelWithoutId = ExpenseMother.getExpenseModelWithoutId();

		Mockito.when(categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(modelWithoutId.getCategory().getDescription())).thenReturn(Optional.empty());
		Mockito.when(categoryRepository.findByDescriptionEqualsIgnoreCase(modelWithoutId.getCategory().getDescription())).thenReturn(modelWithoutId.getCategory());
		Mockito.when(expenseRepository.save(modelWithoutId)).thenReturn(modelWithId);

		final Expense result = expenseServiceImpl.insert(modelWithoutId);
		Assert.assertEquals(result, modelWithId);
	}

	@Test
	public void insertWithCategoryNonExistentTest() {
		final Expense modelWithId =ExpenseMother.getExpenseModelWithId();
		final Expense modelWithoutId = ExpenseMother.getExpenseModelWithoutCategory();

		Mockito.when(categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(modelWithoutId.getCategory().getDescription())).thenReturn(Optional.empty());
		Mockito.when(categoryRepository.findByDescriptionEqualsIgnoreCase(modelWithoutId.getCategory().getDescription())).thenReturn(null);

		Mockito.when(expenseRepository.save(modelWithoutId)).thenReturn(modelWithId);

		final Expense result = expenseServiceImpl.insert(modelWithoutId);
		Assert.assertEquals(result, modelWithId);
	}

	@Test
	public void updateWithCategoryNonExistentTest() {
		final Expense modelWithId =ExpenseMother.getExpenseModelWithId();
		final Expense modelWitCategoryWithoutId = ExpenseMother.getExpenseModelWithCategoryWithoutId();
		final Optional<Expense> optionalExpense = Optional.of(modelWithId);

		Mockito.when(categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(modelWitCategoryWithoutId.getCategory().getDescription())).thenReturn(Optional.empty());
		Mockito.when(categoryRepository.findByDescriptionEqualsIgnoreCase(modelWitCategoryWithoutId.getCategory().getDescription())).thenReturn(null);

		Mockito.when(categoryRepository.save(modelWitCategoryWithoutId.getCategory())).thenReturn(modelWithId.getCategory());
		Mockito.when(categoryRepositoryRedis.insert(modelWithId.getCategory())).thenReturn(modelWithId.getCategory());

		Mockito.when(expenseRepository.save(modelWithId)).thenReturn(modelWithId);
		Mockito.when(expenseRepository.findById(modelWithId.getId())).thenReturn(optionalExpense);

		final Expense result = expenseServiceImpl.update(modelWithId);
		Assert.assertEquals(result, modelWithId);
	}

	@Test
	public void updateWithExistingCategoryAndOptimisticLockErrorTest() {
		exception.expect(OptimisticLockException.class);
		final Expense modelWithId =ExpenseMother.getExpenseModelWithId();
		final Expense modelUpdated = ExpenseMother.getExpenseModelWithId();
		modelUpdated.setVersion(1);
		final Optional<Expense> optionalExpense = Optional.of(modelUpdated);

		Mockito.when(categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(modelWithId.getCategory().getDescription())).thenReturn(Optional.empty());
		Mockito.when(categoryRepository.findByDescriptionEqualsIgnoreCase(modelWithId.getCategory().getDescription())).thenReturn(modelWithId.getCategory());

		Mockito.when(expenseRepository.findById(modelWithId.getId())).thenReturn(optionalExpense);

		expenseServiceImpl.update(modelWithId);
	}
}