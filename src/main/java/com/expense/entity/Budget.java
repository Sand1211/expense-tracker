package com.expense.entity;

import jakarta.persistence.*;

@Entity
public class Budget {

    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer month;

    private Integer year;

    private Double amount;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}


