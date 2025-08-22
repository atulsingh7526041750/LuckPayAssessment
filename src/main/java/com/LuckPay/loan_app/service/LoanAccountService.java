package com.LuckPay.loan_app.service;



import com.LuckPay.loan_app.dto.LoanAccountResponse;
import com.LuckPay.loan_app.integration.ExternalApiIntegration;
import com.LuckPay.loan_app.model.EMIDetail;
import com.LuckPay.loan_app.model.ExternalApiResponse;
import com.LuckPay.loan_app.model.ExternalEMIDetail;
import com.LuckPay.loan_app.model.LoanAccount;
import com.LuckPay.loan_app.repository.EMIDetailRepository;
import com.LuckPay.loan_app.repository.LoanAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class LoanAccountService {
    private static final Logger logger = LoggerFactory.getLogger(LoanAccountService.class);
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy");

    private final ExternalApiIntegration externalApiIntegration;
    private final LoanAccountRepository loanAccountRepository;
    private final EMIDetailRepository emiDetailRepository;

    public LoanAccountService(ExternalApiIntegration externalApiIntegration,
                              LoanAccountRepository loanAccountRepository,
                              EMIDetailRepository emiDetailRepository) {
        this.externalApiIntegration = externalApiIntegration;
        this.loanAccountRepository = loanAccountRepository;
        this.emiDetailRepository = emiDetailRepository;
    }

    @Transactional
    public LoanAccountResponse processLoanAccount(String loanAccountNumber) {
        logger.info("Processing loan account: {}", loanAccountNumber);

        // Check if already exists in DB
        Optional<LoanAccount> existingAccount = loanAccountRepository.findByLoanAccountNumber(loanAccountNumber);
        if (existingAccount.isPresent()) {
            logger.info("Loan account {} already exists in database", loanAccountNumber);
            LoanAccount account = existingAccount.get();
            return new LoanAccountResponse(
                    account.getLoanAccountNumber(),
                    account.getDueDate(),
                    account.getEmiAmount()
            );
        }

        // Call external API
        ExternalApiResponse externalResponse = externalApiIntegration.getLoanAccountDetails(loanAccountNumber);

        // Find the EMI with paidStatus = false (due EMI)
        Optional<ExternalEMIDetail> dueEmi = externalResponse.getEmiDetails().stream()
                .filter(emi -> Boolean.FALSE.equals(emi.getPaidStatus()))
                .findFirst();

        if (dueEmi.isEmpty()) {
            logger.warn("No due EMI found for loan account: {}", loanAccountNumber);
            throw new RuntimeException("No due EMI found for loan account: " + loanAccountNumber);
        }

        ExternalEMIDetail dueEmiDetail = dueEmi.get();

        // Parse due date from month string (assuming format "Month YYYY")
        LocalDate dueDate = parseDueDate(dueEmiDetail.getMonth());

        // Save to database
        LoanAccount loanAccount = new LoanAccount(
                externalResponse.getLoanAccountNumber(),
                dueDate,
                dueEmiDetail.getEmiAmount()
        );

        LoanAccount savedAccount = loanAccountRepository.save(loanAccount);

        // Save all EMI details
        externalResponse.getEmiDetails().forEach(externalEmi -> {
            EMIDetail emiDetail = new EMIDetail(
                    externalEmi.getMonth(),
                    externalEmi.getEmiAmount(),
                    externalEmi.getPaidStatus(),
                    externalEmi.getDueStatus()
            );
            emiDetail.setLoanAccount(savedAccount);
            emiDetailRepository.save(emiDetail);
        });

        logger.info("Successfully saved loan account {} and EMI details", loanAccountNumber);

        return new LoanAccountResponse(
                savedAccount.getLoanAccountNumber(),
                savedAccount.getDueDate(),
                savedAccount.getEmiAmount()
        );
    }

    private LocalDate parseDueDate(String monthString) {
        try {
            // Assuming format like "April 2024" - we'll set due date to 15th of that month
            String[] parts = monthString.split(" ");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid month format: " + monthString);
            }

            int year = Integer.parseInt(parts[1]);
            int month = switch (parts[0].toLowerCase()) {
                case "january" -> 1;
                case "february" -> 2;
                case "march" -> 3;
                case "april" -> 4;
                case "may" -> 5;
                case "june" -> 6;
                case "july" -> 7;
                case "august" -> 8;
                case "september" -> 9;
                case "october" -> 10;
                case "november" -> 11;
                case "december" -> 12;
                default -> throw new IllegalArgumentException("Invalid month: " + parts[0]);
            };

            return LocalDate.of(year, month, 15);

        } catch (Exception e) {
            logger.warn("Failed to parse due date from: {}, using current date", monthString, e);
            return LocalDate.now().plusDays(15); // Fallback
        }
    }
}