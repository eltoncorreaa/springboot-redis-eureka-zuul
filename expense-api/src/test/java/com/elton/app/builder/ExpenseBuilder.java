package com.elton.app.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elton.app.domain.Category;
import com.elton.app.domain.Expense;
import com.elton.app.dto.ExpenseDTO;


/**
 * Class used in test classes for the purpose of creating
 * reusable entities. View in: http://martinfowler.com/bliki/ObjectMother.html
 */
@Component
public class ExpenseBuilder {

	public static final String DESCRIPTION_DTO_TEST = "Description Category";

	public static Expense getExpenseModelWithId() {
		final Expense expense = new Expense();
		expense.setId(1L);
		expense.setCategory(CategoryBuilder.getCategoryModelWithId());
		expense.setExpenseDate(LocalDateTime.of(2018, 5, 1, 0, 0, 0));
		expense.setUserCode(1L);
		expense.setValue(20);
		expense.setVersion(0);
		return expense;
	}

	public static Expense getExpenseModelWithoutId() {
		final Expense expense = new Expense();
		expense.setCategory(CategoryBuilder.getCategoryModelWithId());
		expense.setExpenseDate(LocalDateTime.of(2018, 5, 1, 0, 0, 0));
		expense.setUserCode(1L);
		expense.setValue(20);
		expense.setVersion(0);
		return expense;
	}

	public static Expense getExpenseModelWithCategoryWithoutId() {
		final Expense expense = new Expense();
		expense.setCategory(CategoryBuilder.getCategoryModelWithoutId());
		expense.setExpenseDate(LocalDateTime.of(2018, 5, 1, 0, 0, 0));
		expense.setUserCode(1L);
		expense.setValue(20);
		expense.setVersion(0);
		return expense;
	}

	public static Expense getExpenseModelWithoutCategory() {
		final Expense expense = new Expense();
		expense.setId(1L);
		expense.setCategory(new Category());
		expense.setExpenseDate(LocalDateTime.of(2018, 5, 1, 0, 0, 0));
		expense.setUserCode(1L);
		expense.setValue(20);
		expense.setVersion(0);
		return expense;
	}

	public static ExpenseDTO getExpenseDTOPattern() {
		final ExpenseDTO dto = new ExpenseDTO();
		dto.setCode(1L);
		dto.setDescription(DESCRIPTION_DTO_TEST);
		dto.setDate(LocalDateTime.of(2018, 5, 1, 0, 0, 0));
		dto.setUserCode(1L);
		dto.setValue(20);
		dto.setVersion(0);
		return dto;
	}

	public static List<Expense> getListExpenseModelPattern() {
		final List<Expense> list = new ArrayList<>();
		list.add(getExpenseModelWithId());
		return list;
	}
}
