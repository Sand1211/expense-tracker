package com.expense.service;

import java.util.List;

import com.expense.dto.RecurringExpenseRequest;
import com.expense.dto.UpcomingPaymentDto;
import com.expense.entity.RecurringExpense;

public interface RecurringExpenseService {

	void saveRecurringExpense(RecurringExpenseRequest request);

	List<RecurringExpense> getRecurringExpenses();

	void deleteRecurringExpense(Long id);
	
	
			void updateRecurringExpense(
			        Long id,
			        RecurringExpenseRequest request);
			
			List<UpcomingPaymentDto> getUpcomingPayments();
			


}
