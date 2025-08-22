package com.LuckPay.loan_app.dto;


import java.time.LocalDate;

public class LoanAccountResponse {
    private String loanAccountNumber;
    private LocalDate dueDate;
    private Double emiAmount;

    // Constructors
    public LoanAccountResponse() {}

    public LoanAccountResponse(String loanAccountNumber, LocalDate dueDate, Double emiAmount) {
        this.loanAccountNumber = loanAccountNumber;
        this.dueDate = dueDate;
        this.emiAmount = emiAmount;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(Double emiAmount) {
        this.emiAmount = emiAmount;
    }
}