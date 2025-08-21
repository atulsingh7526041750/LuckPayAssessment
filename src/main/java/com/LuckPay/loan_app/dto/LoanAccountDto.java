package com.LuckPay.loan_app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.math.BigDecimal;
import java.time.LocalDate;


public class LoanAccountDto {


    @JsonProperty("loanAccountNumber")
    private String loanAccountNumber;


    @JsonProperty("dueDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;


    @JsonProperty("emiAmount")
    private BigDecimal emiAmount;


    public LoanAccountDto() {
    }


    public LoanAccountDto(String loanAccountNumber, LocalDate dueDate, BigDecimal emiAmount) {
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


    public BigDecimal getEmiAmount() {
        return emiAmount;
    }


    public void setEmiAmount(BigDecimal emiAmount) {
        this.emiAmount = emiAmount;
    }
}