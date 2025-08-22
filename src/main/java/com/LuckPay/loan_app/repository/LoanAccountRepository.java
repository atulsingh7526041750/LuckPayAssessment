package com.LuckPay.loan_app.repository;



import com.LuckPay.loan_app.entity.LoanAccountEntity;
import com.LuckPay.loan_app.model.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Long> {
    Optional<LoanAccount> findByLoanAccountNumber(String loanAccountNumber);
    boolean existsByLoanAccountNumber(String loanAccountNumber);
}
