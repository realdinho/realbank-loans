package com.realdiv.loans.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.realdiv.loans.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByCustomerIdOrderByStartDtDesc(int customerId);
    
}
