package  com.elton.app.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.elton.app.exception.ExpenseNotFoundException;
import com.elton.app.exception.OptimisticLockException;
import com.elton.app.repository.CategoryRepository;
import com.elton.app.repository.ExpenseRepository;
import com.elton.app.repository.redis.CategoryRepositoryRedis;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EXPENSE")
@SequenceGenerator(name = "SEQUENCE_EXPENSE", sequenceName = "SEQUENCE_EXPENSE")
@Getter @Setter @EqualsAndHashCode
public class Expense implements Serializable {

	private static final long serialVersionUID = -8507622473380945770L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQUENCE_EXPENSE")
	@Column(name = "ID_EXPENSE", precision = 12, scale = 0)
	private Long id;

	@Column(name = "VALUE", nullable = false)
	private double value;

	@Column(name = "USER_CODE", nullable = false)
	private Long userCode;

	@Column(name = "EXPENSE_DATE", nullable = false)
	private LocalDateTime expenseDate;

	@Version
	@Column(name = "VERSION", nullable = false)
	private Integer version;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_CATEGORY", referencedColumnName = "ID_CATEGORY", nullable = true)
	private Category category;

	@Transient @Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private ExpenseRepository expenseRepository;
	@Transient @Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private CategoryRepository categoryRepository;
	@Transient @Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private CategoryRepositoryRedis categoryRepositoryRedis;

	private static final String OPTIMISTIC_LOCK = "The Expense was update by another transaction.";
	private static final int SECOND_5 = 5;
	private static final int SECOND_59 = 59;
	private static final int MINUTES_59 = 59;
	private static final int HOURS_23 = 23;

	public Expense (){}

	public Expense(final ExpenseRepository expenseRepository){
		this.expenseRepository = expenseRepository;
	}

	public Expense(final ExpenseRepository expenseRepository, final CategoryRepository categoryRepository, final CategoryRepositoryRedis categoryRepositoryRedis){
		this.expenseRepository = expenseRepository;
		this.categoryRepository = categoryRepository;
		this.categoryRepositoryRedis = categoryRepositoryRedis;
	}

	public Expense insert(final Expense expense) {
		expense.setCategory(new Category(categoryRepository, categoryRepositoryRedis).categorizeExpenses(expense.getCategory().getDescription()));
		return expenseRepository.save(expense);
	}

	public Expense update(final Expense expense) {
		expense.setCategory(new Category(categoryRepository, categoryRepositoryRedis).categorizeExpenses(expense.getCategory().getDescription()));
		validateLockOptimistic(expense);
		return expenseRepository.save(expense);
	}

	public Page<Expense> findExpensesByUserCode(final Long userCode, final Pageable pageable) {
		final Page<Expense> expenses = expenseRepository.findByUserCodeAndExpenseDateBefore(userCode,
				getLocalDateTimeMinus5Seconds(), pageable);
		if (expenses.getContent().isEmpty()) {
			throw new ExpenseNotFoundException("Expenses not found for user with code: " + userCode);
		}
		return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
	}

	public Page<Expense> findExpensesByFilter(final LocalDateTime date, final Long userCode, final Pageable pageable) {
		final Page<Expense> expenses = expenseRepository.findByUserCodeAndExpenseDateBetween(userCode,
				getLocalDateTimeStartTime(date), getLocalDateEndTime(date), pageable);
		if (expenses.getContent().isEmpty()) {
			throw new ExpenseNotFoundException("Expenses not found for user with code: " + userCode + " and date: " + date);
		}
		return new PageImpl<>(expenses.getContent(), pageable, expenses.getTotalElements());
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

	private void validateLockOptimistic(final Expense expense) {
		final Optional<Expense> result = expenseRepository.findById(expense.getId());
		if (result.isPresent() && result.get().getVersion().compareTo(expense.getVersion()) != 0) {
			throw new OptimisticLockException(OPTIMISTIC_LOCK);
		}
	}
}