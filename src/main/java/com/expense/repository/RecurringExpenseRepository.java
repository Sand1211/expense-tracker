package com.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense.entity.RecurringExpense;

@Repository
public interface RecurringExpenseRepository extends JpaRepository<RecurringExpense, Long> {

	List<RecurringExpense> findByUserUsername(String username);

}
