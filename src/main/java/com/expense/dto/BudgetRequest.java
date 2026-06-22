
package com.expense.dto;

import lombok.Data;

@Data
public class BudgetRequest {

    private Integer month;

    private Integer year;

    private Double amount;
    
    private Boolean applyToAllMonths;

	public Boolean getApplyToAllMonths() {
		return applyToAllMonths;
	}

	public void setApplyToAllMonths(Boolean applyToAllMonths) {
		this.applyToAllMonths = applyToAllMonths;
	}

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
