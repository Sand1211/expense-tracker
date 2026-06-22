package com.expense.dto;

import java.util.List;

public class ExpenseSummaryWrapperResponse {

	private List<ExpenseSummaryResponse> summary;

	private Double grandTotal;

	private Double monthlyBudget;

	private Double monthlySavings;
	
	private Double remainingBudget;

	private Double projectedSpend;

	private Double dailyBudgetLeft;

	private String budgetStatus;

	public Double getRemainingBudget() {
		return remainingBudget;
	}

	public void setRemainingBudget(Double remainingBudget) {
		this.remainingBudget = remainingBudget;
	}

	public Double getProjectedSpend() {
		return projectedSpend;
	}

	public void setProjectedSpend(Double projectedSpend) {
		this.projectedSpend = projectedSpend;
	}

	public Double getDailyBudgetLeft() {
		return dailyBudgetLeft;
	}

	public void setDailyBudgetLeft(Double dailyBudgetLeft) {
		this.dailyBudgetLeft = dailyBudgetLeft;
	}

	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public List<ExpenseSummaryResponse> getSummary() {
		return summary;
	}

	public void setSummary(List<ExpenseSummaryResponse> summary) {
		this.summary = summary;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Double getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(Double monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

	public Double getMonthlySavings() {
		return monthlySavings;
	}

	public void setMonthlySavings(Double monthlySavings) {
		this.monthlySavings = monthlySavings;
	}

}