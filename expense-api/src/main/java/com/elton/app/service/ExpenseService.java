package  com.elton.app.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.elton.app.model.Expense;

public interface ExpenseService {

	Expense insert(Expense expense);

	Expense update(Expense expense);

	Page<Expense> findExpensesByUserCode(Long userCode, Pageable pageable);

	Page<Expense> findExpensesByFilter(LocalDateTime date, Long userCode, Pageable pageable);
}
