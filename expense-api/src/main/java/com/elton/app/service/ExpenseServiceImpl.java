package com.elton.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.app.domain.Expense;
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

	@Transactional(readOnly = false)
	@Override
	public Expense insert(final Expense expense) {
		return new Expense(expenseRepository, categoryRepository, categoryRepositoryRedis).insert(expense);
	}

	@Transactional(readOnly = false)
	@Override
	public Expense update(final Expense expense) {
		return new Expense(expenseRepository, categoryRepository, categoryRepositoryRedis).update(expense);
	}

	@Override
	public Page<Expense> findExpensesByUserCode(final Long userCode, final Pageable pageable) {
		return new Expense(expenseRepository).findExpensesByUserCode(userCode, pageable);
	}

	@Override
	public Page<Expense> findExpensesByFilter(final LocalDateTime date, final Long userCode, final Pageable pageable) {
		return new Expense(expenseRepository).findExpensesByFilter(date, userCode, pageable);
	}
}