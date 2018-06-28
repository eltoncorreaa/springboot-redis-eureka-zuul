package  com.elton.app.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.elton.app.converter.ExpenseConverter;
import com.elton.app.dto.ExpenseDTO;
import com.elton.app.service.ExpenseService;

@CrossOrigin
@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/api/v1/expenses")
	public ResponseEntity<?> insert(@RequestBody final ExpenseDTO dto){
		final ExpenseDTO result= ExpenseConverter.toDTO(expenseService.insert(ExpenseConverter.fromDTO(dto)));
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/api/v1/expenses")
	public ResponseEntity<?> update(@RequestBody final ExpenseDTO dto){
		expenseService.update(ExpenseConverter.fromDTO(dto));
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/v1/expenses/{userCode}")
	public ResponseEntity<?> findExpensesByUserCode(@PathVariable final Long userCode, final Pageable pageable){
		return new ResponseEntity<>(expenseService.findExpensesByUserCode(userCode, pageable), HttpStatus.OK);
	}

	@GetMapping("/api/v1/expenses")
	public ResponseEntity<?> findExpensesByFilter(final ExpenseDTO dto, final Pageable pageable){
		return new ResponseEntity<>(expenseService.findExpensesByFilter(dto.getDate(), dto.getUserCode(), pageable), HttpStatus.OK);
	}
}
