package com.elton.app.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

	private static final String OPTIMISTIC_LOCK = "The Expense was update by another transaction.";
	private static final int SECOND_5 = 5;
	private static final int SECOND_59 = 59;
	private static final int MINUTES_59 = 59;
	private static final int HOURS_23 = 23;


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
		Category category = findCategoryInDatabases(description);
		if (description != null && category == null) {
			category = new Category();
			category.setDescription(description);
			return categoryRepositoryRedis.insert(categoryRepository.save(category));
		}
		return category;
	}

	private Category findCategoryInDatabases(final String description) {
		final Optional<Category> category = categoryRepositoryRedis.findByDescriptionEqualsIgnoreCase(description);
		return category.isPresent() ? category.get() : categoryRepository.findByDescriptionEqualsIgnoreCase(description);
	}

	private void validateLockOptimistic(final Expense expense) {
		final Optional<Expense> result = expenseRepository.findById(expense.getId());
		if (result.isPresent() && result.get().getVersion().compareTo(expense.getVersion()) != 0) {
			throw new OptimisticLockException(OPTIMISTIC_LOCK);
		}
	}

	public static LocalDateTime getLocalDateTimeMinus5Seconds() {
		return LocalDateTime.now().minusSeconds(SECOND_5);
	}

	public static LocalDateTime getLocalDateTimeStartTime(final LocalDateTime expenseDate) {
		return LocalDateTime.of(expenseDate.getYear(), expenseDate.getMonth(), expenseDate.getDayOfMonth(), 0, 0, 0);
	}

	public static LocalDateTime getLocalDateEndTime(final LocalDateTime expenseDate) {
		return LocalDateTime.of(expenseDate.getYear(), expenseDate.getMonth(), expenseDate.getDayOfMonth(), HOURS_23, MINUTES_59, SECOND_59);
	}
}
