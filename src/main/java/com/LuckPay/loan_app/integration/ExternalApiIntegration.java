package com.LuckPay.loan_app.integration;



import com.LuckPay.loan_app.exception.ExternalApiException;
import com.LuckPay.loan_app.model.ExternalApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Component
public class ExternalApiIntegration {
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiIntegration.class);

    @Value("${external.api.url:http://demo9993930.mockable.io/loanaccount/}")
    private String externalApiUrl;

    private final RestTemplate restTemplate;

    public ExternalApiIntegration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExternalApiResponse getLoanAccountDetails(String loanAccountNumber) {
        String url = externalApiUrl + loanAccountNumber;
        logger.info("Calling external API: {}", url);

        try {
            ResponseEntity<ExternalApiResponse> response = restTemplate.getForEntity(
                    url, ExternalApiResponse.class);

            logger.info("External API response status: {}", response.getStatusCode());
            return response.getBody();

        } catch (HttpClientErrorException e) {
            logger.error("Client error when calling external API: {}", e.getStatusCode(), e);
            throw new ExternalApiException("Client error when calling external API: " + e.getStatusCode(), e);
        } catch (HttpServerErrorException e) {
            logger.error("Server error when calling external API: {}", e.getStatusCode(), e);
            throw new ExternalApiException("Server error when calling external API: " + e.getStatusCode(), e);
        } catch (RestClientException e) {
            logger.error("Error calling external API: {}", e.getMessage(), e);
            throw new ExternalApiException("Error calling external API: " + e.getMessage(), e);
        }
    }
}