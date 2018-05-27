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

import com.elton.app.converter.ExpenseConverter;
import com.elton.app.dto.ExpenseDTO;
import com.elton.app.exception.OptimisticLockException;
import com.elton.app.model.Category;
import com.elton.app.model.Expense;
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
	public void insertWithExistingCategoryTest() {
		final ExpenseDTO dto =ExpenseMother.getExpenseDTOPattern();
		final Category category= CategoryMother.getCategoryModelWithIdPattern();

		final Expense expenseModel= ExpenseConverter.fromDTO(dto);
		expenseModel.setCategory(category);

		Mockito.when(expenseRepository.save(expenseModel)).thenReturn(expenseModel);

		final ExpenseDTO dtoReturn = expenseServiceImpl.insert(dto);
		Assert.assertEquals(expenseModel, ExpenseConverter.fromDTO(dtoReturn));
		Assert.assertEquals(expenseModel.getCategory().getDescription(), ExpenseConverter.fromDTO(dtoReturn).getCategory().getDescription());
	}

	@Test
	public void insertWithCategoryNonexistentTest() {
		final ExpenseDTO dto =ExpenseMother.getExpenseDTOPattern();
		final Category categoryWithId= CategoryMother.getCategoryModelWithIdPattern();

		final Expense expenseModel= ExpenseConverter.fromDTO(dto);
		expenseModel.setCategory(categoryWithId);

		Mockito.when(expenseRepository.save(expenseModel)).thenReturn(expenseModel);

		final ExpenseDTO dtoReturn = expenseServiceImpl.insert(dto);
		Assert.assertEquals(expenseModel, ExpenseConverter.fromDTO(dtoReturn));
		Assert.assertEquals(expenseModel.getCategory().getDescription(), ExpenseConverter.fromDTO(dtoReturn).getCategory().getDescription());
	}

	@Test
	public void updateWithExistingCategoryTest() {
		final ExpenseDTO dto =ExpenseMother.getExpenseDTOPattern();
		final Category category= CategoryMother.getCategoryModelWithIdPattern();

		final Expense expenseModel= ExpenseConverter.fromDTO(dto);
		expenseModel.setCategory(category);
		final Optional<Expense> optionalExpense = Optional.of(expenseModel);

		Mockito.when(expenseRepository.save(expenseModel)).thenReturn(expenseModel);
		Mockito.when(expenseRepository.findById(expenseModel.getId())).thenReturn(optionalExpense);

		final ExpenseDTO dtoReturn = expenseServiceImpl.update(dto);
		Assert.assertEquals(expenseModel, ExpenseConverter.fromDTO(dtoReturn));
		Assert.assertEquals(expenseModel.getCategory().getDescription(), ExpenseConverter.fromDTO(dtoReturn).getCategory().getDescription());
	}

	@Test
	public void updateWithExistingCategoryAndOptimisticLockErrorTest() {
		exception.expect(OptimisticLockException.class);
		final ExpenseDTO dto =ExpenseMother.getExpenseDTOPattern();

		final Category category= CategoryMother.getCategoryModelWithIdPattern();

		final Expense expenseModel= ExpenseConverter.fromDTO(dto);
		expenseModel.setCategory(category);
		final Optional<Expense> optionalExpense = Optional.of(expenseModel);

		dto.setVersion(1);
		Mockito.when(expenseRepository.findById(expenseModel.getId())).thenReturn(optionalExpense);

		expenseServiceImpl.update(dto);
	}
}
