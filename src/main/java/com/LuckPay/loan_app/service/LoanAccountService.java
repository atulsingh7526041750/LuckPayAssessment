package com.LuckPay.loan_app.service;


import com.LuckPay.loan_app.dto.LoanAccountDto;
import com.LuckPay.loan_app.entity.LoanAccountEntity;
import com.LuckPay.loan_app.integration.ExternalLoanClient;
import com.LuckPay.loan_app.repository.LoanAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@Service
public class LoanAccountService {


    private static final Logger logger = LoggerFactory.getLogger(LoanAccountService.class);


    private final ExternalLoanClient externalLoanClient;
    private final LoanAccountRepository repository;


    public LoanAccountService(ExternalLoanClient externalLoanClient, LoanAccountRepository repository) {
        this.externalLoanClient = externalLoanClient;
        this.repository = repository;
    }


    @Transactional
    public LoanAccountDto fetchAndPersist(String loanAccountNumber) {
        logger.info("Fetching loan account from external service: {}", loanAccountNumber);
        LoanAccountDto dto = externalLoanClient.fetchLoanAccount(loanAccountNumber);


// check if already present and update or save
        Optional<LoanAccountEntity> existing = repository.findByLoanAccountNumber(dto.getLoanAccountNumber());
        LoanAccountEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setDueDate(dto.getDueDate());
            entity.setEmiAmount(dto.getEmiAmount());
            logger.debug("Updating existing loan account entity with id={}", entity.getId());
        } else {
            entity = new LoanAccountEntity(dto.getLoanAccountNumber(), dto.getDueDate(), dto.getEmiAmount());
            logger.debug("Creating new loan account entity for {}", dto.getLoanAccountNumber());
        }


        LoanAccountEntity saved = repository.save(entity);
        logger.info("Persisted loan account. id={}, account={}", saved.getId(), saved.getLoanAccountNumber());


// return same DTO to controller (could map from entity if needed)
        return dto;
    }


    public Optional<LoanAccountDto> findPersisted(String loanAccountNumber) {
        return repository.findByLoanAccountNumber(loanAccountNumber)
                .map(e -> new LoanAccountDto(e.getLoanAccountNumber(), e.getDueDate(), e.getEmiAmount()));
    }
}