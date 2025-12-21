package com.LuckPay.loan_app.controller;

import com.LuckPay.loan_app.dto.LoanAccountResponse;

import com.LuckPay.loan_app.service.LoanAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loanaccounts")
public class LoanAccountController {
    private static final Logger logger = LoggerFactory.getLogger(LoanAccountController.class);

    private final LoanAccountService loanAccountService;

    public LoanAccountController(LoanAccountService loanAccountService) {
        this.loanAccountService = loanAccountService;
    }

    @GetMapping("/healthCheck")
    public String checkHealth(){
        System.out.println("this is the code change in dev 1 ");
        return "Health is ohk";
    }

    @GetMapping("/{loanAccountNumber}")
    public ResponseEntity<LoanAccountResponse> getLoanAccountDetails(
            @PathVariable String loanAccountNumber) {

        logger.info("Received request for loan account: {}", loanAccountNumber);

        LoanAccountResponse response = loanAccountService.processLoanAccount(loanAccountNumber);
        logger.info("Successfully processed loan account: {}", loanAccountNumber);
        return ResponseEntity.ok(response);
    }
}