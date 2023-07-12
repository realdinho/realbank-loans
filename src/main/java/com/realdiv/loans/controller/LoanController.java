package com.realdiv.loans.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.realdiv.loans.config.LoanConfig;
import com.realdiv.loans.model.Loan;
import com.realdiv.loans.model.Property;
import com.realdiv.loans.repository.LoanRepository;

@RestController
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
 
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    LoanConfig loanConfig;

    @GetMapping("/{customerId}")
    public List<Loan> getLoansList(
        @RequestHeader("realbank-correlation-id") String correlationId,
        @PathVariable int customerId
    ) {
        logger.info("RealBank - getting loans of customer {}. Correlation ID: {}", customerId, correlationId);
        List<Loan> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customerId);
        logger.info("RealBank - found {} loans of customer {}", loans.size(), customerId);
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
