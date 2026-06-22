
package com.expense.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.dto.BudgetRequest;
import com.expense.entity.Budget;
import com.expense.entity.User;
import com.expense.repository.BudgetRepository;
import com.expense.repository.UserRepository;


@RestController
@RequestMapping("/budget")
public class BudgetController {

	private final BudgetRepository budgetRepository;

	private final UserRepository userRepository;

	public BudgetController(BudgetRepository budgetRepository ,UserRepository userRepository) {
		this.budgetRepository=budgetRepository;
		this.userRepository=userRepository;
	}

	@PostMapping
	public ResponseEntity<String> saveBudget(@RequestBody BudgetRequest request) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByUsername(username).orElseThrow();

		if (Boolean.TRUE.equals(request.getApplyToAllMonths())) {

			for (int month = 1; month <= 12; month++) {

				Optional<Budget> optionalBudget = budgetRepository.findByMonthAndYearAndUserUsername(month,
						request.getYear(), username);

				Budget budget;

				if (optionalBudget.isPresent()) {

					budget = optionalBudget.get();

				} else {

					budget = new Budget();

					budget.setMonth(month);

					budget.setYear(request.getYear());

					budget.setUser(user);
				}

				budget.setAmount(request.getAmount());

				budgetRepository.save(budget);
			}

			return ResponseEntity.ok("Budget applied to all months");
		}

		Optional<Budget> optionalBudget = budgetRepository.findByMonthAndYearAndUserUsername(request.getMonth(),
				request.getYear(), username);

		Budget budget;

		if (optionalBudget.isPresent()) {

			budget = optionalBudget.get();

		} else {

			budget = new Budget();

			budget.setMonth(request.getMonth());

			budget.setYear(request.getYear());

			budget.setUser(user);
		}

		budget.setAmount(request.getAmount());

		budgetRepository.save(budget);

		return ResponseEntity.ok("Budget saved successfully");
	}

	@GetMapping
	public ResponseEntity<?> getBudget(@RequestParam Integer month, @RequestParam Integer year) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Budget> optionalBudget = budgetRepository.findByMonthAndYearAndUserUsername(month, year, username);

		if (optionalBudget.isEmpty()) {

			return ResponseEntity.ok(0);
		}

		return ResponseEntity.ok(optionalBudget.get().getAmount());
	}

}
