package com.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.entity.Reminder;

public interface ReminderRepository
        extends JpaRepository<Reminder, Long> {

    List<Reminder> findByUserUsernameOrderByReminderDateDesc(
            String username);

    Long countByUserUsernameAndIsReadFalse(
            String username);
    
    boolean existsByRecurringExpenseIdAndMessage(
            Long recurringExpenseId,
            String message);
    
    List<Reminder> findByUserUsernameAndIsReadFalse(
            String username);

}