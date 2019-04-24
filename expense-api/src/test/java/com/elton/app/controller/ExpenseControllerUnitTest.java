package com.elton.app.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.elton.app.builder.ExpenseBuilder;
import com.elton.app.converter.ExpenseConverter;
import com.elton.app.domain.Expense;
import com.elton.app.dto.ExpenseDTO;
import com.elton.app.service.ExpenseService;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseControllerUnitTest {

	@InjectMocks
	private ExpenseController expenseController;

	@Mock
	private ExpenseService expenseService;

	private static final int STATUS_CODE_SUCESS = 200;

	@Test
	public void insertTest() {
		final Expense domain= ExpenseConverter.toDomain(ExpenseBuilder.getExpenseDTOPattern());
		final ExpenseDTO dto = ExpenseBuilder.getExpenseDTOPattern();

		Mockito.when(expenseService.insert(domain)).thenReturn(domain);

		final ResponseEntity<ExpenseDTO> result = expenseController.insert(dto);
		Assert.assertEquals(result.getBody(), dto);
		Assert.assertEquals(result.getStatusCodeValue(), STATUS_CODE_SUCESS);
	}

	@Test
	public void updateTest() {
		final Expense domain= ExpenseConverter.toDomain(ExpenseBuilder.getExpenseDTOPattern());
		final ExpenseDTO dto = ExpenseBuilder.getExpenseDTOPattern();

		Mockito.when(expenseService.update(domain)).thenReturn(domain);

		final ResponseEntity<ExpenseDTO> result = expenseController.update(dto);
		Assert.assertEquals(result.getBody(), dto);
		Assert.assertEquals(result.getStatusCodeValue(), STATUS_CODE_SUCESS);
	}

	@Test
	public void findExpensesByUserCodeTest() {
		final Page<Expense> page= new PageImpl<>(ExpenseBuilder.getListExpenseModelPattern());
		Mockito.when(expenseService.findExpensesByUserCode(1L, null)).thenReturn(page);

		final Page<ExpenseDTO> pageDTO= new PageImpl<>(ExpenseConverter.fromDomain(ExpenseBuilder.getListExpenseModelPattern()));
		final ResponseEntity<Page<ExpenseDTO>> result = expenseController.findExpensesByUserCode(1L, null);

		Assert.assertEquals(result.getBody().getContent(), pageDTO.getContent());
		Assert.assertEquals(result.getStatusCodeValue(), STATUS_CODE_SUCESS);
	}

	@Test
	public void findExpensesByFilterTest() {
		final ExpenseDTO dto = ExpenseBuilder.getExpenseDTOPattern();

		final Page<Expense> page= new PageImpl<>(ExpenseBuilder.getListExpenseModelPattern());
		Mockito.when(expenseService.findExpensesByFilter(dto.getDate(), dto.getUserCode(), null)).thenReturn(page);

		final Page<ExpenseDTO> pageDTO= new PageImpl<>(ExpenseConverter.fromDomain(ExpenseBuilder.getListExpenseModelPattern()));
		final ResponseEntity<Page<ExpenseDTO>> result = expenseController.findExpensesByFilter(dto, null);

		Assert.assertEquals(result.getBody().getContent(), pageDTO.getContent());
		Assert.assertEquals(result.getStatusCodeValue(), STATUS_CODE_SUCESS);
	}
}
