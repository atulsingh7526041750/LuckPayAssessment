package com.LuckPay.loan_app.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "loan_accounts")
public class LoanAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_account_number", unique = true)
    private String loanAccountNumber;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "emi_amount")
    private Double emiAmount;

    @OneToMany(mappedBy = "loanAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EMIDetail> emiDetails;

    // Constructors
    public LoanAccount() {}

    public LoanAccount(String loanAccountNumber, LocalDate dueDate, Double emiAmount) {
        this.loanAccountNumber = loanAccountNumber;
        this.dueDate = dueDate;
        this.emiAmount = emiAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<EMIDetail> getEmiDetails() {
        return emiDetails;
    }

    public void setEmiDetails(List<EMIDetail> emiDetails) {
        this.emiDetails = emiDetails;
    }

}