package com.expense.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.expense.dto.RecurringExpenseRequest;
import com.expense.dto.UpcomingPaymentDto;
import com.expense.entity.RecurringExpense;
import com.expense.entity.User;
import com.expense.repository.RecurringExpenseRepository;
import com.expense.repository.UserRepository;
import com.expense.service.RecurringExpenseService;

@Service
public class RecurringExpenseServiceImpl implements RecurringExpenseService {

	private final RecurringExpenseRepository recurringExpenseRepository;
	private final UserRepository userRepository;

	public RecurringExpenseServiceImpl(UserRepository userRepository,
			RecurringExpenseRepository recurringExpenseRepository) {
		this.recurringExpenseRepository = recurringExpenseRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void saveRecurringExpense(RecurringExpenseRequest request) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByUsername(username).orElseThrow();

		RecurringExpense recurringExpense = new RecurringExpense();

		recurringExpense.setName(request.getName());

		recurringExpense.setCategory(request.getCategory());

		recurringExpense.setAmount(request.getAmount());

		recurringExpense.setDueDay(request.getDueDay());

		recurringExpense.setStartDate(request.getStartDate());

		recurringExpense.setEndDate(request.getEndDate());

		recurringExpense.setReminderDaysBefore(request.getReminderDaysBefore());

		recurringExpense.setVariableAmount(request.getVariableAmount());

		recurringExpense.setAutoAddExpense(request.getAutoAddExpense());

		recurringExpense.setActive(request.getActive());

		recurringExpense.setRecurringType(request.getRecurringType());

		recurringExpense.setUser(user);

		recurringExpenseRepository.save(recurringExpense);
	}

	@Override
	public List<RecurringExpense> getRecurringExpenses() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return recurringExpenseRepository.findByUserUsername(username);
	}

	@Override
	public void deleteRecurringExpense(Long id) {

		recurringExpenseRepository.deleteById(id);
	}

	@Override
	public void updateRecurringExpense(Long id, RecurringExpenseRequest request) {

		RecurringExpense expense = recurringExpenseRepository.findById(id).orElseThrow();

		expense.setName(request.getName());

		expense.setCategory(request.getCategory());

		expense.setAmount(request.getAmount());

		expense.setDueDay(request.getDueDay());

		expense.setStartDate(request.getStartDate());

		expense.setEndDate(request.getEndDate());

		expense.setReminderDaysBefore(request.getReminderDaysBefore());

		expense.setVariableAmount(request.getVariableAmount());

		expense.setAutoAddExpense(request.getAutoAddExpense());

		expense.setActive(request.getActive());

		expense.setRecurringType(request.getRecurringType());

		recurringExpenseRepository.save(expense);
	}

	@Override
	public List<UpcomingPaymentDto>
	getUpcomingPayments() {

	    String username =
	            SecurityContextHolder
	                    .getContext()
	                    .getAuthentication()
	                    .getName();

	    LocalDate today =
	            LocalDate.now();

	    return recurringExpenseRepository
	            .findByUserUsername(username)
	            .stream()

	            .filter(
	                RecurringExpense::getActive
	            )

	            .map(expense -> {

	            	LocalDate dueDate =
	            	        LocalDate.of(
	            	                today.getYear(),
	            	                today.getMonth(),
	            	                expense.getDueDay());

	            	if (dueDate.isBefore(today)) {

	            	    dueDate = dueDate.plusMonths(1);
	            	}

	            	long daysRemaining =
	            	        ChronoUnit.DAYS.between(
	            	                today,
	            	                dueDate);


	                return new UpcomingPaymentDto(

	                        expense.getId(),

	                        expense.getName(),

	                        expense.getAmount(),

	                        expense.getDueDay(),

	                        expense.getRecurringType()
	                               .name(),

	                        daysRemaining
	                );
	            })

	            .sorted(
	                Comparator.comparingLong(
	                    UpcomingPaymentDto
	                        ::getDaysRemaining
	                )
	            )

	            .toList();
	}
	


}
