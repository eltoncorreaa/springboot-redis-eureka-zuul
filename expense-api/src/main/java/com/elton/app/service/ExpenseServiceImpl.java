package com.elton.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.app.exception.ExpenseNotFoundException;
import com.elton.app.exception.OptimisticLockException;
import com.elton.app.model.Category;
import com.elton.app.model.Expense;
import com.elton.app.repository.CategoryRepository;
import com.elton.app.repository.ExpenseRepository;
import com.elton.app.repository.redis.CategoryRepositoryRedis;

@Transactional(readOnly = true)
@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryRepositoryRedis categoryRepositoryRedis;

	private static final String OPTIMISTIC_lOCK = "The Expense was update by another transaction.";

	@Transactional(readOnly = false)
	@Override
	public Expense insert(final Expense expense) {
		expense.setCategory(categorizeExpenses(expense.getCategory().getDescription()));
		return expenseRepository.save(expense);
	}

	@Transactional(readOnly = false)
	@Override
	public Expense update(final Expense expense) {
		expense.setCategory(categorizeExpenses(expense.getCategory().getDescription()));
		validateLockOptimistic(expense);
		return expenseRepository.save(expense);
	}

	@Override
	public Page<Expense> findExpensesByUserCode(final Long userCode, final Pageable pageable) {
		final Page<Expense> expenses = expenseRepository.findByUserCodeAndExpenseDateBefore(userCode,
				getLocalDateTimeMinus5Seconds(), pageable);
		if (expenses.getContent().isEmpty()) {
			throw new ExpenseNotFoundException("Expenses not found for user with code: " + userCode);
		}
		return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
	}

	@Override
	public Page<Expense> findExpensesByFilter(final LocalDateTime date, final Long userCode, final Pageable pageable) {
		final Page<Expense> expenses = expenseRepository.findByUserCodeAndExpenseDateBetween(userCode,
				getLocalDateTimeStartTime(date), getLocalDateEndTime(date), pageable);
		if (expenses.getContent().isEmpty()) {
			throw new ExpenseNotFoundException(
					"Expenses not found for user with code: " + userCode + " and date: " + date);
		}
		return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
	}

	@Transactional(readOnly = false)
	public Category categorizeExpenses(final String description) {
		Category category = categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(description);
		if (category == null) {
			category = categoryRepository.findByDescriptionEqualsIgnoreCase(description);
		}
		if (description != null && category == null) {
			category = new Category();
			category.setDescription(description);
			return categoryRepositoryRedis.insert(categoryRepository.save(category));
		}
		return category;
	}

	private void validateLockOptimistic(final Expense expense) {
		if (!expenseRepository.findById(expense.getId()).get().getVersion().equals(expense.getVersion())) {
			throw new OptimisticLockException(OPTIMISTIC_lOCK);
		}
	}

	public static LocalDateTime getLocalDateTimeMinus5Seconds() {
		return LocalDateTime.now().minusSeconds(5);
	}

	public static LocalDateTime getLocalDateTimeStartTime(final LocalDateTime expenseDate) {
		return LocalDateTime.of(expenseDate.getYear(), expenseDate.getMonth(), expenseDate.getDayOfMonth(), 0, 0, 0);
	}

	public static LocalDateTime getLocalDateEndTime(final LocalDateTime expenseDate) {
		return LocalDateTime.of(expenseDate.getYear(), expenseDate.getMonth(), expenseDate.getDayOfMonth(), 23, 59, 59);
	}
}
