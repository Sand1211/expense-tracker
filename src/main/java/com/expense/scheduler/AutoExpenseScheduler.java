package com.expense.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.expense.entity.Expense;
import com.expense.entity.RecurringExpense;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.RecurringExpenseRepository;

@Component
public class AutoExpenseScheduler {

    private final RecurringExpenseRepository recurringExpenseRepository;

    private final ExpenseRepository expenseRepository;

    public AutoExpenseScheduler(
            RecurringExpenseRepository recurringExpenseRepository,
            ExpenseRepository expenseRepository) {

        this.recurringExpenseRepository =
                recurringExpenseRepository;

        this.expenseRepository =
                expenseRepository;
    }

    @Scheduled(cron = "0 0 22 * * *")
   // @Scheduled(cron = "0 */1 * * * ?")
    public void createRecurringExpenses() {

        LocalDate today =
                LocalDate.now();

        List<RecurringExpense> expenses =
                recurringExpenseRepository.findAll();
        

        for (RecurringExpense recurringExpense :
                expenses) {
        	

            if (!Boolean.TRUE.equals(
                    recurringExpense.getActive())) {

                continue;
            }

            if (!Boolean.TRUE.equals(
                    recurringExpense.getAutoAddExpense())) {

                continue;
            }
           
            
            if (Boolean.TRUE.equals(
                    recurringExpense.getVariableAmount())) {


                continue;
            }

            Integer dueDay =
                    recurringExpense.getDueDay();

            if (dueDay == null ||
                    today.getDayOfMonth() != dueDay) {

                continue;
            }
            if (recurringExpense
                    .getLastAutoCreatedDate() != null
                    &&
                recurringExpense
                    .getLastAutoCreatedDate()
                    .getMonth()
                    .equals(
                        today.getMonth())
                    &&
                recurringExpense
                    .getLastAutoCreatedDate()
                    .getYear()
                    ==
                today.getYear()) {

                continue;
            }

            Expense expense =
                    new Expense();

            expense.setAmount(
                    recurringExpense.getAmount());

            expense.setCategory(
                    recurringExpense.getCategory());

            expense.setDescription(
                    "Auto-created from recurring expense: "
                    + recurringExpense.getName());

            expense.setExpenseDate(
                    today);

            expense.setUser(
                    recurringExpense.getUser());

            expenseRepository.save(
                    expense);

            recurringExpense
                    .setLastAutoCreatedDate(
                            today);

            recurringExpenseRepository.save(
                    recurringExpense);

        }
    }
}