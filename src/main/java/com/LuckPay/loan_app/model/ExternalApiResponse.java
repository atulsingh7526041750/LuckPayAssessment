package com.LuckPay.loan_app.model;


import java.util.List;

public class ExternalApiResponse {
    private String loanAccountNumber;
    private List<ExternalEMIDetail> emiDetails;

    public ExternalApiResponse(String loanAccountNumber, List<ExternalEMIDetail> emiDetails) {
        this.loanAccountNumber = loanAccountNumber;
        this.emiDetails = emiDetails;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public List<ExternalEMIDetail> getEmiDetails() {
        return emiDetails;
    }

    public void setEmiDetails(List<ExternalEMIDetail> emiDetails) {
        this.emiDetails = emiDetails;
    }

}