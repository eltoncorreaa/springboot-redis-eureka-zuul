package com.elton.app.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.elton.app.domain.Category;
import com.elton.app.domain.Expense;
import com.elton.app.dto.ExpenseDTO;

public class ExpenseConverter {

	public static Expense toDomain(final ExpenseDTO dto) {
		final Expense expense = new Expense();
		final Category category = new Category();
		expense.setCategory(category);
		expense.setId(dto.getCode());
		expense.getCategory().setDescription(dto.getDescription());
		expense.setUserCode(dto.getUserCode());
		expense.setExpenseDate(dto.getDate());
		expense.setValue(dto.getValue());
		expense.setVersion(dto.getVersion());
		return expense;
	}

	public static ExpenseDTO fromDomain(final Expense model) {
		final ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setCode(model.getId());
		if (model.getCategory() != null) {
			expenseDTO.setDescription(model.getCategory().getDescription());
		}
		expenseDTO.setUserCode(model.getUserCode());
		expenseDTO.setDate(model.getExpenseDate());
		expenseDTO.setValue(model.getValue());
		expenseDTO.setVersion(model.getVersion());
		return expenseDTO;
	}

	public static List<ExpenseDTO> fromDomain(final List<Expense> listModel) {
		return listModel.stream().map(ExpenseConverter::fromDomain).collect(Collectors.toList());
	}
}
