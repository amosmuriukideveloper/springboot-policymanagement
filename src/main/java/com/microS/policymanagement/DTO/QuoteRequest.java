package com.microS.policymanagement.DTO;

import com.microS.policymanagement.models.CoverageOptions;
import com.microS.policymanagement.models.CoverageType;
import com.microS.policymanagement.models.Policy;
import com.microS.policymanagement.models.PolicyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class QuoteRequest {
    private String makeModel;
    private int manufactureYear;
    private Policy policy;
    private CoverageType coverageType;
    private PolicyType policyType;
    private Set<CoverageOptions> coverageOptions;
}
