package com.LuckPay.loan_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.LuckPay.loan_app")
public class LoanAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(LoanAppApplication.class, args);

		System.out.println("this is Lock Pay application");
	}

}
