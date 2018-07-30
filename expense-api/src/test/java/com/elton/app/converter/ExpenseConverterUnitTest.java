package com.elton.app.converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.elton.app.dto.ExpenseDTO;
import com.elton.app.model.Expense;
import com.elton.app.objectfactory.ExpenseMother;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseConverterUnitTest { 

	@Test
	public void fromDTOTest() {
		final Expense expenseConverted = ExpenseConverter.fromDTO(ExpenseMother.getExpenseDTOPattern());
		final Expense expense= ExpenseMother.getExpenseModelWithId();
		expense.getCategory().setId(null);
		Assert.assertEquals(expense, expenseConverted);
	}

	@Test
	public void toDTOTest() {
		final ExpenseDTO expenseConverted = ExpenseConverter.toDTO(ExpenseMother.getExpenseModelWithId());
		final ExpenseDTO expense= ExpenseMother.getExpenseDTOPattern();
		Assert.assertEquals(expense, expenseConverted);
	}

	@Test
	public void toListDTOTest() {
		Expense expense= ExpenseMother.getExpenseModelWithId();
		expense.setCategory(null);
		final List<Expense> lista = new ArrayList<>();
		lista.add(ExpenseMother.getExpenseModelWithId());
		lista.add(ExpenseMother.getExpenseModelWithId());
		lista.add(expense);

		final List<ExpenseDTO> listDTO = ExpenseConverter.toDTO(lista);
		Assert.assertEquals(lista.size(), listDTO.size());
	}
}
