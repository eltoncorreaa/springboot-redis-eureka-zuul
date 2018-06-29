/*package com.elton.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.elton.app.model.Expense;
import com.elton.app.objectfactory.ExpenseMother;
import com.elton.app.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ExpenseController.class, secure = false)
public class ExpenseControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@InjectMocks
	private ExpenseController expenseController;

	@MockBean
	private ExpenseService expenseService;

	@Test
	public void insertTest() throws Exception {
		final Expense model = ExpenseMother.getExpenseModelWithId();
		Mockito.when(expenseService.insert(model)).thenReturn(model);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/expenses")
				.accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(model));
		final ResultActions result = mvc.perform(post("/api/v1/expenses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(model)))
				.andExpect(status().is2xxSuccessful());

		//result.getResponse().getContentAsString();
	}
}*/