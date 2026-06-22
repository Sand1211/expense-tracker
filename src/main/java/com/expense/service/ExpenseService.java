package com.expense.service;

import java.io.IOException;
import java.util.List;


import com.expense.dto.BudgetVsExpenseResponse;
import com.expense.dto.ExpenseRequest;
import com.expense.dto.ExpenseResponse;
import com.expense.dto.ExpenseSearchRequest;
import com.expense.dto.ExpenseSearchResponse;
import com.expense.dto.ExpenseSummaryWrapperResponse;
import com.expense.entity.Expense;

import jakarta.servlet.http.HttpServletResponse;

public interface ExpenseService {

	Expense addExpense(ExpenseRequest request);

	List<ExpenseResponse> getAllExpenses();

	Expense getExpenseById(Long id);

	void deleteExpense(Long id);

	Expense updateExpense(Long id, ExpenseRequest request);
	
	ExpenseSearchResponse searchExpenses(
	        ExpenseSearchRequest request);
	ExpenseSummaryWrapperResponse
	getCategorySummary(
	        Integer month,
	        Integer year);
	
	public void exportExpensesToExcel(String month,String year,String searchText,
            HttpServletResponse response) throws IOException;

	
	List<BudgetVsExpenseResponse> getBudgetVsExpenseTrend();

	
}