package com.expense.dto;

public class BudgetVsExpenseResponse {

    private String month;

    private Double budget;

    private Double expense;

    public BudgetVsExpenseResponse() {
    }

    public BudgetVsExpenseResponse(
            String month,
            Double budget,
            Double expense) {

        this.month = month;
        this.budget = budget;
        this.expense = expense;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }
}