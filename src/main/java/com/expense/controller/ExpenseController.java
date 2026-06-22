package com.expense.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.dto.ExpenseRequest;
import com.expense.dto.ExpenseResponse;
import com.expense.dto.ExpenseSearchRequest;
import com.expense.dto.ExpenseSearchResponse;
import com.expense.dto.ExpenseSummaryWrapperResponse;
import com.expense.entity.Expense;
import com.expense.service.ExpenseService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@PostMapping
	public ResponseEntity<Expense> addExpense(@RequestBody ExpenseRequest request) {

		return new ResponseEntity<>(expenseService.addExpense(request), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {

		return ResponseEntity.ok(expenseService.getAllExpenses());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Expense> getExpenseById(@PathVariable("id") Long id) {

		return ResponseEntity.ok(expenseService.getExpenseById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable("id") Long id) {

		expenseService.deleteExpense(id);

		return ResponseEntity.ok("Expense deleted successfully");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Expense> updateExpense(@PathVariable("id") Long id, @RequestBody ExpenseRequest request) {

		return ResponseEntity.ok(expenseService.updateExpense(id, request));
	}

	@PostMapping("/search")
	public ResponseEntity<ExpenseSearchResponse> searchExpenses(@RequestBody ExpenseSearchRequest request) {

		return ResponseEntity.ok(expenseService.searchExpenses(request));
	}

	@GetMapping("/summary")
	public ResponseEntity<ExpenseSummaryWrapperResponse> getCategorySummary(
			@RequestParam(required = false) Integer month,

			@RequestParam(required = false) Integer year) {

		return ResponseEntity.ok(expenseService.getCategorySummary(month, year));
	}

	@GetMapping("/export")
	public void exportExpensesToExcel(@RequestParam(required = false) String month,
			@RequestParam(required = false) String year, @RequestParam(required = false) String searchText,
			HttpServletResponse response) throws IOException {

		expenseService.exportExpensesToExcel(month,year,searchText, response);
	}
	
	@GetMapping("/budget-vs-expense")
	public ResponseEntity<?> getBudgetVsExpenseTrend() {

	    return ResponseEntity.ok(
	            expenseService
	            .getBudgetVsExpenseTrend());
	}

}