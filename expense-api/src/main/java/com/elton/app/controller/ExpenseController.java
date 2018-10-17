package  com.elton.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elton.app.converter.ExpenseConverter;
import com.elton.app.domain.Expense;
import com.elton.app.dto.ExpenseDTO;
import com.elton.app.service.ExpenseService;

@CrossOrigin
@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/api/v1/expenses")
	public ResponseEntity<ExpenseDTO> insert(@RequestBody final ExpenseDTO dto){
		final ExpenseDTO result= ExpenseConverter.fromDomain(expenseService.insert(ExpenseConverter.toDomain(dto)));
		return new ResponseEntity<ExpenseDTO>(result, HttpStatus.OK);
	}

	@PutMapping("/api/v1/expenses")
	public ResponseEntity<ExpenseDTO> update(@RequestBody final ExpenseDTO dto){
		final ExpenseDTO result = ExpenseConverter.fromDomain(expenseService.update(ExpenseConverter.toDomain(dto)));
		return new ResponseEntity<ExpenseDTO>(result, HttpStatus.OK);
	}

	@GetMapping("/api/v1/expenses/{userCode}")
	public ResponseEntity<Page<ExpenseDTO>> findExpensesByUserCode(@PathVariable final Long userCode, final Pageable pageable){
		final Page<Expense> page = expenseService.findExpensesByUserCode(userCode, pageable);
		final Page<ExpenseDTO> result=  new PageImpl<>(ExpenseConverter.fromDomain(page.getContent()), page.getPageable(), page.getTotalElements());
		return new ResponseEntity<Page<ExpenseDTO>>(result, HttpStatus.OK);
	}

	@GetMapping("/api/v1/expenses")
	public ResponseEntity<Page<ExpenseDTO>> findExpensesByFilter(final ExpenseDTO dto, final Pageable pageable){
		final Page<Expense> page = expenseService.findExpensesByFilter(dto.getDate(), dto.getUserCode(), pageable);
		final Page<ExpenseDTO> result=  new PageImpl<>(ExpenseConverter.fromDomain(page.getContent()), page.getPageable(), page.getTotalElements());
		return new ResponseEntity<Page<ExpenseDTO>>(result, HttpStatus.OK);
	}
}
