package com.expense.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.dto.RecurringExpenseRequest;
import com.expense.dto.UpcomingPaymentDto;
import com.expense.entity.RecurringExpense;
import com.expense.service.RecurringExpenseService;


@RestController
@RequestMapping("/recurring-expenses")
public class RecurringExpenseController {

	private final RecurringExpenseService recurringExpenseService;

	public RecurringExpenseController(RecurringExpenseService recurringExpenseService) {
		this.recurringExpenseService=recurringExpenseService;
	}

	@PostMapping
	public ResponseEntity<String> saveRecurringExpense(@RequestBody RecurringExpenseRequest request) {

		recurringExpenseService.saveRecurringExpense(request);

		return ResponseEntity.ok("Recurring expense saved successfully");
	}

	@GetMapping
	public ResponseEntity<List<RecurringExpense>> getRecurringExpenses() {

		return ResponseEntity.ok(recurringExpenseService.getRecurringExpenses());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRecurringExpense(@PathVariable Long id) {

		recurringExpenseService.deleteRecurringExpense(id);

		return ResponseEntity.ok("Recurring expense deleted successfully");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateRecurringExpense(@PathVariable Long id,

			@RequestBody RecurringExpenseRequest request) {

		recurringExpenseService.updateRecurringExpense(id, request);

		return ResponseEntity.ok("Recurring expense updated successfully");
	}
	
	@GetMapping("/upcoming")

	public ResponseEntity<
	        List<UpcomingPaymentDto>>
	getUpcomingPayments() {

	    return ResponseEntity.ok(

	        recurringExpenseService
	                .getUpcomingPayments()
	    );
	}


}
