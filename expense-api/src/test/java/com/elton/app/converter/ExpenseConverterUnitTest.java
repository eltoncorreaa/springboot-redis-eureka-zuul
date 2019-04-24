package com.elton.app.converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.elton.app.builder.ExpenseBuilder;
import com.elton.app.domain.Expense;
import com.elton.app.dto.ExpenseDTO;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseConverterUnitTest { 

	@Test
	public void fromDTOTest() {
		final Expense expenseConverted = ExpenseConverter.toDomain(ExpenseBuilder.getExpenseDTOPattern());
		final Expense expense= ExpenseBuilder.getExpenseModelWithId();
		expense.getCategory().setId(null);
		Assert.assertEquals(expense, expenseConverted);
	}

	@Test
	public void toDTOTest() {
		final ExpenseDTO expenseConverted = ExpenseConverter.fromDomain(ExpenseBuilder.getExpenseModelWithId());
		final ExpenseDTO expense= ExpenseBuilder.getExpenseDTOPattern();
		Assert.assertEquals(expense, expenseConverted);
	}

	@Test
	public void toListDTOTest() {
		Expense expense= ExpenseBuilder.getExpenseModelWithId();
		expense.setCategory(null);
		final List<Expense> lista = new ArrayList<>();
		lista.add(ExpenseBuilder.getExpenseModelWithId());
		lista.add(ExpenseBuilder.getExpenseModelWithId());
		lista.add(expense);

		final List<ExpenseDTO> listDTO = ExpenseConverter.fromDomain(lista);
		Assert.assertEquals(lista.size(), listDTO.size());
	}
}
