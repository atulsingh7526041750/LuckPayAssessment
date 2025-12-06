package com.LuckPay.loan_app.exception;


public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {

        super(message, cause);
        System.out.println("Till herer====================================+++++++++++"+message);
        System.out.println("Till herer====================================+++++++++++"+cause);
    }
}