package com.expense.entity;

import java.time.LocalDate;

import com.expense.dto.enums.RecurringType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recurring_expenses")
public class RecurringExpense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Integer reminderDaysBefore;

	public Long getId() {
		return id;
	}

	public Integer getReminderDaysBefore() {
		return reminderDaysBefore;
	}

	public void setReminderDaysBefore(Integer reminderDaysBefore) {
		this.reminderDaysBefore = reminderDaysBefore;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private String category;

	private LocalDate startDate;

	private LocalDate endDate;

	private Double amount;

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

	private Integer dueDay;

	private Boolean variableAmount;

	private Boolean autoAddExpense;
	
	private LocalDate lastAutoCreatedDate;
	
	public LocalDate getLastAutoCreatedDate() {
	    return lastAutoCreatedDate;
	}

	public void setLastAutoCreatedDate(
	        LocalDate lastAutoCreatedDate) {

	    this.lastAutoCreatedDate =
	            lastAutoCreatedDate;
	}

	private Boolean active;

	@Enumerated(EnumType.STRING)
	private RecurringType recurringType;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
