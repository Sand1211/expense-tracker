package com.expense.dto;

import java.time.LocalDate;

import com.expense.entity.Expense;

public class ExpenseResponse {

    private Long id;
    private Double amount;
    private String category;
    private String description;
    private LocalDate expenseDate;
   private String  profileImage;
	public String getProfileImage() {
	return profileImage;
}
   public void setProfileImage(String profileImage) {
	this.profileImage = profileImage;
   }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}
	public ExpenseResponse(Expense val) {
	    this.id = val.getId();
	    this.amount = val.getAmount();
	    this.description = val.getDescription();
	    this.category = val.getCategory();
	    this.expenseDate = val.getExpenseDate();
	}
	
	

}
