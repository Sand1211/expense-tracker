
package com.expense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.entity.Budget;

public interface BudgetRepository
        extends JpaRepository<Budget, Long> {

    Optional<Budget> findByMonthAndYearAndUserUsername(
            Integer month,
            Integer year,
            String username);
}

