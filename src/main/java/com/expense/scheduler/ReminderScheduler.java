package com.expense.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.expense.service.impl.ReminderService;

@Component
public class ReminderScheduler {

    private final ReminderService reminderService;

    public ReminderScheduler(
            ReminderService reminderService) {

        this.reminderService =
                reminderService;
    }

    @Scheduled(cron = "0 0 22 * * *")
   // @Scheduled(cron = "0 */1 * * * ?")
    public void createReminders() {
        reminderService.generateReminders();
    }
}