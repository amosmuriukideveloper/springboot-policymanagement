package com.microS.policymanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    private int manufactureYear;

    private CoverageType coverageType;

    private PolicyType policyType;

    private Set<CoverageOptions> coverageOptions;

    private BigDecimal totalPremium;

}
