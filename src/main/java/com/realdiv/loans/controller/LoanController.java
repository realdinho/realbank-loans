package com.realdiv.loans.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.realdiv.loans.config.LoanConfig;
import com.realdiv.loans.model.Loan;
import com.realdiv.loans.model.Property;
import com.realdiv.loans.repository.LoanRepository;

@RestController
@RequestMapping("loans")
public class LoanController {
 
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    LoanConfig loanConfig;

    @GetMapping("/{customerId}")
    public List<Loan> getLoansList(
        @PathVariable int customerId
    ) {
        List<Loan> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customerId);
        return loans;
    }

    @GetMapping("/properties")
    public String getProperties() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Property props = new Property(
                            loanConfig.getMsg(), 
                            loanConfig.getBuildVersion(), 
                            loanConfig.getMailDetails(), 
                            loanConfig.getActiveBranches()
                        );
        return ow.writeValueAsString(props);
    }
}
