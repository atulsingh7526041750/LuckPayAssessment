package com.LuckPay.loan_app.model;

public class ExternalEMIDetail {
    private String month;
    private Double emiAmount;
    private Boolean paidStatus;
    private Boolean dueStatus;

    public ExternalEMIDetail(String month, Double emiAmount, Boolean paidStatus, Boolean dueStatus) {
        this.month = month;
        this.emiAmount = emiAmount;
        this.paidStatus = paidStatus;
        this.dueStatus = dueStatus;
    }

    public ExternalEMIDetail() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(Double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public Boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Boolean paidStatus) {
        this.paidStatus = paidStatus;
    }

    public Boolean getDueStatus() {
        return dueStatus;
    }

    public void setDueStatus(Boolean dueStatus) {
        this.dueStatus = dueStatus;
    }
}
