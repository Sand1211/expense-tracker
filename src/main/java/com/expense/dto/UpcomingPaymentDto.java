package com.expense.dto;

public class UpcomingPaymentDto {

    private Long id;

    private String name;

    private Double amount;

    private Integer dueDay;

    private String recurringType;

    private long daysRemaining;

    public UpcomingPaymentDto() {

    }

    public UpcomingPaymentDto(
            Long id,
            String name,
            Double amount,
            Integer dueDay,
            String recurringType,
            long daysRemaining) {

        this.id = id;
        this.name = name;
        this.amount = amount;
        this.dueDay = dueDay;
        this.recurringType = recurringType;
        this.daysRemaining = daysRemaining;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDueDay() {
        return dueDay;
    }

    public void setDueDay(Integer dueDay) {
        this.dueDay = dueDay;
    }

    public String getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(String recurringType) {
        this.recurringType = recurringType;
    }

    public long getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }
}
