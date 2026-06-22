package com.expense.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.expense.dto.BudgetVsExpenseResponse;
import com.expense.dto.ExpenseRequest;
import com.expense.dto.ExpenseResponse;
import com.expense.dto.ExpenseSearchRequest;
import com.expense.dto.ExpenseSearchResponse;
import com.expense.dto.ExpenseSummaryResponse;
import com.expense.dto.ExpenseSummaryWrapperResponse;
import com.expense.dto.specification.ExpenseSpecification;
import com.expense.entity.Budget;
import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.repository.BudgetRepository;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import com.expense.service.ExpenseService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository expenseRepository;

	private final UserRepository userRepository;

	private final BudgetRepository budgetRepository;

	public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository,
			BudgetRepository budgetRepository) {

		this.expenseRepository = expenseRepository;
		this.userRepository = userRepository;
		this.budgetRepository = budgetRepository;
	}

	@Override
	public Expense addExpense(ExpenseRequest request) {

		Expense expense = new Expense();

		expense.setAmount(request.getAmount());
		expense.setCategory(request.getCategory().toUpperCase());
		expense.setDescription(request.getDescription());
		expense.setExpenseDate(request.getExpenseDate());

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		expense.setUser(user);

		return expenseRepository.save(expense);
	}

	@Override
	public List<ExpenseResponse> getAllExpenses() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Expense> byUserUsername = expenseRepository.findByUserUsername(username);
		return getResponseList(byUserUsername);
	}

	@Override
	public Expense getExpenseById(Long id) {

		return expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
	}

	@Override
	public void deleteExpense(Long id) {

		Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));

		expenseRepository.delete(expense);
	}

	@Override
	public Expense updateExpense(Long id, ExpenseRequest request) {

		Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));

		expense.setAmount(request.getAmount());
		expense.setCategory(request.getCategory().toUpperCase());
		expense.setDescription(request.getDescription());
		expense.setExpenseDate(request.getExpenseDate());

		return expenseRepository.save(expense);
	}

	@Override
	public ExpenseSearchResponse searchExpenses(ExpenseSearchRequest request) {

		Specification<Expense> specification = Specification
				.where(ExpenseSpecification.hasCategory(request.getCategory()))
				.and(ExpenseSpecification.hasDescription(request.getDescription()))
				.and(ExpenseSpecification.hasSearchText(request.getSearchText()));

		if (request.getMonth() != null && request.getYear() != null) {
			specification = specification.and(ExpenseSpecification.hasMonthYear(request.getMonth(), request.getYear()));
		} else {
			specification = specification.and(ExpenseSpecification.hasDate(request.getExpenseDate()));
		}

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		specification = specification.and(ExpenseSpecification.hasUsername(username));

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("expenseDate").descending());

		Page<Expense> expensePage = expenseRepository.findAll(specification, pageable);

		Double totalExpense = expensePage.getContent().stream().mapToDouble(Expense::getAmount).sum();

		ExpenseSearchResponse response = new ExpenseSearchResponse();

		List<Expense> content = expensePage.getContent();

		response.setExpenses(getResponseList(content));

		response.setTotalExpense(totalExpense);

		response.setTotalRecords(expensePage.getTotalElements());

		response.setCurrentPage(expensePage.getNumber());

		response.setTotalPages(expensePage.getTotalPages());

		return response;
	}

	@Override
	public ExpenseSummaryWrapperResponse getCategorySummary(Integer month, Integer year) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		List<ExpenseSummaryResponse> summary = expenseRepository.getCategorySummary(username, month, year);

		Double grandTotal = summary.stream().mapToDouble(ExpenseSummaryResponse::getTotalAmount).sum();

		ExpenseSummaryWrapperResponse response = new ExpenseSummaryWrapperResponse();

		response.setSummary(summary);
		response.setGrandTotal(grandTotal);

		Double monthlyBudget = 0.0;

		if (month != null && year != null) {

			Optional<Budget> budget = budgetRepository.findByMonthAndYearAndUserUsername(month, year, username);

			monthlyBudget = budget.map(Budget::getAmount).orElse(0.0);
		}

		double remainingBudget = monthlyBudget - grandTotal;

		LocalDate today = LocalDate.now();

		boolean currentMonth = month != null && year != null && month.equals(today.getMonthValue())
				&& year.equals(today.getYear());

		double projectedSpend = grandTotal;
		double dailyBudgetLeft = remainingBudget;
		String budgetStatus = "N/A";

		if (currentMonth) {

			projectedSpend = (grandTotal / today.getDayOfMonth()) * today.lengthOfMonth();

			dailyBudgetLeft = remainingBudget / Math.max(1, today.lengthOfMonth() - today.getDayOfMonth());

			// Current month prediction
			if (monthlyBudget > 0) {

				if (projectedSpend > monthlyBudget) {

					budgetStatus = "Overspending";

				} else {

					budgetStatus = "On Track";
				}
			}

		} else {

			// Past/Future month
			projectedSpend = grandTotal;
			dailyBudgetLeft = remainingBudget;

			if (monthlyBudget > 0) {

				if (grandTotal > monthlyBudget) {

					budgetStatus = "Budget Exceeded";

				} else {

					budgetStatus = "Within Budget";
				}
			}
		}

		response.setRemainingBudget(remainingBudget);

		response.setProjectedSpend(projectedSpend);

		response.setDailyBudgetLeft(dailyBudgetLeft);

		response.setBudgetStatus(budgetStatus);

		response.setMonthlyBudget(monthlyBudget);

		response.setMonthlySavings(remainingBudget);

		return response;
	}

	@Override
	public void exportExpensesToExcel(String month, String year, String searchText, HttpServletResponse response)
			throws IOException {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Specification<Expense> specification = Specification
				.where(ExpenseSpecification.hasSearchText(searchText).and(ExpenseSpecification.hasUsername(username)));
		if (month != null && year != null) {
			specification = specification.and(ExpenseSpecification.hasMonthYear(month, year));
		}

		List<Expense> expenses = expenseRepository.findAll(specification);

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";

		String headerValue = "attachment; filename=expenses.xlsx";

		response.setHeader(headerKey, headerValue);

		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("Expenses");

		// HEADER ROW

		Row headerRow = sheet.createRow(0);

		String[] columns = { "S.No", "Amount", "Category", "Description", "Expense Date" };

		for (int i = 0; i < columns.length; i++) {

			Cell cell = headerRow.createCell(i);

			cell.setCellValue(columns[i]);
		}

		// DATA ROWS

		int rowCount = 1;

		for (Expense expense : expenses) {

			Row row = sheet.createRow(rowCount);

			row.createCell(0).setCellValue(rowCount);

			row.createCell(1).setCellValue(expense.getAmount());

			row.createCell(2).setCellValue(expense.getCategory());

			row.createCell(3).setCellValue(expense.getDescription());

			row.createCell(4).setCellValue(expense.getExpenseDate().toString());

			rowCount++;
		}

		// AUTO SIZE COLUMNS

		for (int i = 0; i < columns.length; i++) {

			sheet.autoSizeColumn(i);
		}

		ServletOutputStream outputStream = response.getOutputStream();

		workbook.write(outputStream);

		workbook.close();

		outputStream.close();
	}

	private List<ExpenseResponse> getResponseList(List<Expense> entity) {

		return entity.stream().map(ExpenseResponse::new).collect(Collectors.toList());
	}

	public List<BudgetVsExpenseResponse> getBudgetVsExpenseTrend() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		List<BudgetVsExpenseResponse> result = new ArrayList<>();

		LocalDate today = LocalDate.now();

		for (int i = 5; i >= 0; i--) {

			LocalDate date = today.minusMonths(i);

			int month = date.getMonthValue();

			int year = date.getYear();

			Double budget = budgetRepository.findByMonthAndYearAndUserUsername(month, year, username)
					.map(Budget::getAmount).orElse(0.0);

			Double expense = expenseRepository.getMonthlyExpense(username, month, year);

			result.add(new BudgetVsExpenseResponse(date.getMonth().name().substring(0, 3), budget, expense));
		}

		return result;
	}

}