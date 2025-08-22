package com.LuckPay.loan_app.repository;


import com.LuckPay.loan_app.model.EMIDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EMIDetailRepository extends JpaRepository<EMIDetail, Long> {
}