package com.expense.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.entity.Reminder;
import com.expense.repository.ReminderRepository;

@RestController
@RequestMapping("/reminders")
public class ReminderController {

    private final ReminderRepository reminderRepository;

    public ReminderController(ReminderRepository reminderRepository) {

        this.reminderRepository = reminderRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reminder>> getMyReminders() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return ResponseEntity.ok(
                reminderRepository
                        .findByUserUsernameOrderByReminderDateDesc(
                                username));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long>
    getUnreadCount() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        

        Long count =
                reminderRepository
                        .countByUserUsernameAndIsReadFalse(
                                username);

        return ResponseEntity.ok(
                count);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String>
    markAsRead(
            @PathVariable Long id) {

    	Reminder reminder =
    	        reminderRepository
    	                .findById(id)
    	                .orElse(null);

    	if (reminder == null) {

    	    return ResponseEntity
    	            .badRequest()
    	            .body("Reminder not found");
    	}


        reminder.setIsRead(true);

        reminderRepository.save(
                reminder);

        return ResponseEntity.ok(
                "Reminder marked as read");
    }
    
    @PutMapping("/mark-all-read")
    public ResponseEntity<String>
    markAllRead() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        List<Reminder> reminders =
                reminderRepository
                        .findByUserUsernameAndIsReadFalse(
                                username);

        reminders.forEach(
                reminder ->
                        reminder.setIsRead(true));

        reminderRepository.saveAll(
                reminders);

        return ResponseEntity.ok(
                "All reminders marked read");
    }
}
