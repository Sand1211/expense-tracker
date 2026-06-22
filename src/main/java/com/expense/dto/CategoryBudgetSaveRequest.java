package com.expense.dto;

import java.util.List;

public class CategoryBudgetSaveRequest {
	 private Integer month;

	    private Integer year;

	    private List<CategoryBudgetItem> budgets;

		public Integer getMonth() {
			return month;
		}

		public void setMonth(Integer month) {
			this.month = month;
		}

		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}

		public List<CategoryBudgetItem> getBudgets() {
			return budgets;
		}

		public void setBudgets(List<CategoryBudgetItem> budgets) {
			this.budgets = budgets;
		}

}
