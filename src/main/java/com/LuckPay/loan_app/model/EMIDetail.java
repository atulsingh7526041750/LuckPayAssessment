package com.LuckPay.loan_app.model;


import jakarta.persistence.*;
@Entity
@Table(name = "emi_details")
public class EMIDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month_period") // Rename to avoid reserved keyword
    private String month;

    @Column(name = "emi_amount")
    private Double emiAmount;

    @Column(name = "paid_status")
    private Boolean paidStatus;

    @Column(name = "due_status")
    private Boolean dueStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_account_id")
    private LoanAccount loanAccount;

    // Constructors, Getters, Setters
    public EMIDetail() {}

    public EMIDetail(String month, Double emiAmount, Boolean paidStatus, Boolean dueStatus) {
        this.month = month;
        this.emiAmount = emiAmount;
        this.paidStatus = paidStatus;
        this.dueStatus = dueStatus;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Double getEmiAmount() { return emiAmount; }
    public void setEmiAmount(Double emiAmount) { this.emiAmount = emiAmount; }

    public Boolean getPaidStatus() { return paidStatus; }
    public void setPaidStatus(Boolean paidStatus) { this.paidStatus = paidStatus; }

    public Boolean getDueStatus() { return dueStatus; }
    public void setDueStatus(Boolean dueStatus) { this.dueStatus = dueStatus; }

    public LoanAccount getLoanAccount() { return loanAccount; }
    public void setLoanAccount(LoanAccount loanAccount) { this.loanAccount = loanAccount; }
}