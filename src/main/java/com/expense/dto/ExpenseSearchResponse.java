package com.expense.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
public class ExpenseSearchResponse {

    private List<ExpenseResponse> expenses;

    private Double totalExpense;

    private Long totalRecords;

    private Integer currentPage;

    private Integer totalPages;

	public ExpenseSearchResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ExpenseResponse> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseResponse> expenses) {
		this.expenses = expenses;
	}

	public Double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	 public ExpenseSearchResponse(
	            List<ExpenseResponse> expenses,
	            Double totalExpense,
	            Long totalRecords,
	            Integer currentPage,
	            Integer totalPages
	    ) {
	        this.expenses = expenses;
	        this.totalExpense = totalExpense;
	        this.totalRecords = totalRecords;
	        this.currentPage = currentPage;
	        this.totalPages = totalPages;
	    }
}