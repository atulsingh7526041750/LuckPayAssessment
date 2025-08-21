package com.LuckPay.loan_app.controller;

import com.LuckPay.loan_app.dto.LoanAccountDto;
import com.LuckPay.loan_app.entity.LoanAccountEntity;
import com.LuckPay.loan_app.exception.LoanAccountNotFoundException;
import com.LuckPay.loan_app.service.LoanAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/loan")
public class LoanAccountController {


    private static final Logger logger = LoggerFactory.getLogger(LoanAccountController.class);


    private final LoanAccountService loanAccountService;


    public LoanAccountController(LoanAccountService loanAccountService) {
        this.loanAccountService = loanAccountService;
    }


    /**
     * Calls the external service for the given loan account number, persists the response and returns the JSON.
     * Example: GET /api/loan/1
     */
    @GetMapping("/{loanAccountNumber}")
    public ResponseEntity<LoanAccountDto> getLoanAccount(@PathVariable("loanAccountNumber") String loanAccountNumber) {
        logger.info("API called: GET /api/loan/{}", loanAccountNumber);
        LoanAccountDto dto = loanAccountService.fetchAndPersist(loanAccountNumber);
        return ResponseEntity.ok(dto);
    }


    /**
     * Returns the persisted loan account (if present) from local DB.
     * Example: GET /api/loan/persisted/1
     */
    @GetMapping("/persisted/{loanAccountNumber}")
    public ResponseEntity<LoanAccountDto> getPersisted(@PathVariable("loanAccountNumber") String loanAccountNumber) {
        logger.info("API called: GET /api/loan/persisted/{}", loanAccountNumber);
        return loanAccountService.findPersisted(loanAccountNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new LoanAccountNotFoundException("Loan account not found in local DB: " + loanAccountNumber));
    }




}