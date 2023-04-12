package com.microS.policymanagement.Controllers;

import com.microS.policymanagement.DTO.QuoteRequest;
import com.microS.policymanagement.Services.QuoteService;
import com.microS.policymanagement.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
    @Autowired
    private QuoteService quoteService;

    @PostMapping("/calculate")
    public ResponseEntity<Quote> calculateQuote(@RequestBody QuoteRequest quoteRequest) {
        String makeModel = quoteRequest.getMakeModel();
        int manufactureYear = quoteRequest.getManufactureYear();
        Policy policy = quoteRequest.getPolicy();
        CoverageType coverageType = quoteRequest.getCoverageType();
        PolicyType policyType = quoteRequest.getPolicyType();
        Set<CoverageOptions> coverageOptions = quoteRequest.getCoverageOptions();

        Quote quote = quoteService.calculateQuote(makeModel, manufactureYear, policy, coverageType, policyType, coverageOptions);
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }
}
