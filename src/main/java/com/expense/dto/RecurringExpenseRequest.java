package com.expense.dto;

import java.time.LocalDate;

import com.expense.dto.enums.RecurringType;

public class RecurringExpenseRequest {

	    private String name;

	    private String category;

	    private Double amount;

	    private Integer dueDay;

	    private LocalDate startDate;

	    private LocalDate endDate;

	    private Integer reminderDaysBefore;

	    private Boolean variableAmount;

	    private Boolean autoAddExpense;

	    private Boolean active;

	    private RecurringType recurringType;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public Integer getDueDay() {
			return dueDay;
		}

		public void setDueDay(Integer dueDay) {
			this.dueDay = dueDay;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}

		public Integer getReminderDaysBefore() {
			return reminderDaysBefore;
		}

		public void setReminderDaysBefore(Integer reminderDaysBefore) {
			this.reminderDaysBefore = reminderDaysBefore;
		}

		public Boolean getVariableAmount() {
			return variableAmount;
		}

		public void setVariableAmount(Boolean variableAmount) {
			this.variableAmount = variableAmount;
		}

		public Boolean getAutoAddExpense() {
			return autoAddExpense;
		}

		public void setAutoAddExpense(Boolean autoAddExpense) {
			this.autoAddExpense = autoAddExpense;
		}

		public Boolean getActive() {
			return active;
		}

		public void setActive(Boolean active) {
			this.active = active;
		}

		public RecurringType getRecurringType() {
			return recurringType;
		}

		public void setRecurringType(RecurringType recurringType) {
			this.recurringType = recurringType;
		}
	


}
