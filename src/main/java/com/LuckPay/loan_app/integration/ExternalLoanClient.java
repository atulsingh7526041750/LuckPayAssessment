package com.LuckPay.loan_app.integration;


import com.LuckPay.loan_app.dto.LoanAccountDto;
import com.LuckPay.loan_app.exception.ExternalServiceException;
import com.LuckPay.loan_app.exception.LoanAccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class ExternalLoanClient {


    private static final Logger logger = LoggerFactory.getLogger(ExternalLoanClient.class);


    private final RestTemplate restTemplate;
    private final String baseUrl;


    public ExternalLoanClient(RestTemplate restTemplate, @Value("${external.loan.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }


    public LoanAccountDto fetchLoanAccount(String loanAccountNumber) {
        String url = String.format("%s/loanaccount/%s", baseUrl, loanAccountNumber);
        logger.debug("Calling external loan service: {}", url);
        try {
            ResponseEntity<LoanAccountDto> resp = restTemplate.getForEntity(url, LoanAccountDto.class);
            if (resp.getStatusCode() == HttpStatus.OK && resp.getBody() != null) {
                logger.debug("Received response from external service for {}", loanAccountNumber);
                return resp.getBody();
            } else {
                logger.warn("External service returned non-OK or empty body: {}", resp.getStatusCode());
                throw new ExternalServiceException("External service returned status: " + resp.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound nf) {
            logger.info("External service returned 404 for {}", loanAccountNumber);
            throw new LoanAccountNotFoundException("Loan account not found in external service: " + loanAccountNumber);
        } catch (RestClientException ex) {
            logger.error("Error while calling external service: {}", ex.getMessage());
            throw new ExternalServiceException("Failed to call external loan service", ex);
        }
    }
}