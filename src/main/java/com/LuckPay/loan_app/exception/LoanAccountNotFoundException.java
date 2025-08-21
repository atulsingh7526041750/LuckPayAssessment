package com.LuckPay.loan_app.exception;

public class LoanAccountNotFoundException extends RuntimeException {
    public LoanAccountNotFoundException(String message) {
        super(message);
    }
}