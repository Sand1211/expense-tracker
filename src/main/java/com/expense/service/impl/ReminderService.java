package com.expense.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.expense.entity.RecurringExpense;
import com.expense.entity.Reminder;
import com.expense.repository.RecurringExpenseRepository;
import com.expense.repository.ReminderRepository;

@Service
public class ReminderService {

	private final ReminderRepository reminderRepository;
	private final RecurringExpenseRepository recurringExpenseRepository;

	public ReminderService(ReminderRepository reminderRepository,
			RecurringExpenseRepository recurringExpenseRepository) {

		this.reminderRepository = reminderRepository;
		this.recurringExpenseRepository = recurringExpenseRepository;
	}

	public void generateReminders() {
		
		LocalDate today = LocalDate.now();

		List<RecurringExpense> expenses = recurringExpenseRepository.findAll();


		for (RecurringExpense expense : expenses) {

			if (!expense.getActive()) {
				continue;
			}

			Integer dueDay = expense.getDueDay();

			if (dueDay == null) {
				continue;
			}

			LocalDate dueDate = LocalDate.of(today.getYear(), today.getMonth(), dueDay);

			if (dueDate.isBefore(today)) {

				dueDate = dueDate.plusMonths(1);
			}

			LocalDate reminderDate = dueDate.minusDays(expense.getReminderDaysBefore());


			if (!today.equals(reminderDate)) {
				continue;
			}

			Reminder reminder = new Reminder();

			reminder.setMessage(expense.getName() + " is due on " + dueDate);

			reminder.setReminderDate(LocalDateTime.now());

			reminder.setIsRead(false);

			reminder.setUser(expense.getUser());

			reminder.setRecurringExpense(expense);

			String message = expense.getName() + " is due on " + dueDate;

			boolean exists = reminderRepository.existsByRecurringExpenseIdAndMessage(expense.getId(), message);

			if (exists) {

				continue;
			}
			reminder.setMessage(message);


			reminderRepository.save(reminder);
		}
	}
}
