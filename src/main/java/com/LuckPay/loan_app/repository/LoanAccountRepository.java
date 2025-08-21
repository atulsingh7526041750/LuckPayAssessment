package com.LuckPay.loan_app.repository;


import com.LuckPay.loan_app.entity.LoanAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccountEntity, Long> {
    Optional<LoanAccountEntity> findByLoanAccountNumber(String loanAccountNumber);
}