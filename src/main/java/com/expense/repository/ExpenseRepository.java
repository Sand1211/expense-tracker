package com.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.expense.dto.ExpenseSummaryResponse;
import com.expense.entity.Expense;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long>,
                JpaSpecificationExecutor<Expense> {
	
	
	
	@Query("""
	       SELECT new com.expense.dto.ExpenseSummaryResponse(
	            e.category,
	            SUM(e.amount)
	       )
	       FROM Expense e

	       WHERE e.user.username = :username

	       AND (:month IS NULL
	              OR EXTRACT(MONTH FROM e.expenseDate) = :month)

	       AND (:year IS NULL
	            OR EXTRACT(YEAR FROM e.expenseDate) = :year)

	       GROUP BY e.category
	       """)
	List<ExpenseSummaryResponse> getCategorySummary(
	        String username,
	        Integer month,
	        Integer year);
	

	
	
			List<Expense> findByUserUsername(
			        String username);
			
			
			
			@Query("""
					SELECT COALESCE(SUM(e.amount),0)
					FROM Expense e
					WHERE e.user.username = :username
					AND EXTRACT(MONTH FROM e.expenseDate) = :month
					AND EXTRACT(YEAR FROM e.expenseDate) = :year
					""")
					Double getMonthlyExpense(
					        String username,
					        Integer month,
					        Integer year);

}