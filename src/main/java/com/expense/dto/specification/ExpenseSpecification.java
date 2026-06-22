package com.expense.dto.specification;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.data.jpa.domain.Specification;

import com.expense.entity.Expense;

public class ExpenseSpecification {

    public static Specification<Expense> hasCategory(
            String category) {

        return (root, query, cb) ->
                category == null
                        ? null
                        : cb.equal(
                                root.get("category"),
                                category.toUpperCase());
    }
    
    public static Specification<Expense> hasMonthYear(String month, String year) {

        return (root, query, cb) -> {

            if (month == null || month.trim().isEmpty() || month.equals("null") || year.equals("null") ||
                year == null || year.trim().isEmpty()) {
                return null;
            }

            int m;
            int y;

            try {
                m = Integer.parseInt(month);
                y = Integer.parseInt(year);
            } catch (NumberFormatException e) {
                return null; // or throw validation exception
            }

            YearMonth ym = YearMonth.of(y, m);

            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();

            return cb.between(root.get("expenseDate"), start, end);
        };
    }
    
  
    		public static Specification<Expense>
    		hasUsername(String username) {

    		    return (root, query, cb) ->

    		            cb.equal(
    		                    root.get("user")
    		                        .get("username"),
    		                    username);
    		}
    		


    public static Specification<Expense> hasDescription(
            String description) {

        return (root, query, cb) ->
                description == null
                        ? null
                        : cb.like(
                                cb.lower(root.get("description")),
                                "%" + description.toLowerCase() + "%");
    }

    public static Specification<Expense> hasDate(
            LocalDate date) {

        return (root, query, cb) ->
                date == null
                        ? null
                        : cb.equal(root.get("expenseDate"), date);
    }
    
    public static Specification<Expense> hasSearchText(
            String searchText) {

        return (root, query, cb) -> {

            if (searchText == null ||
                    searchText.trim().isEmpty()) {

                return null;
            }

            String pattern =
                    "%" + searchText.toLowerCase() + "%";

            return cb.or(

                    cb.like(
                            cb.lower(root.get("category")),
                            pattern),

                    cb.like(
                            cb.lower(root.get("description")),
                            pattern)
            );
        };
    }
}