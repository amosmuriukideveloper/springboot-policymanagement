package com.microS.policymanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private CoverageType coverageType;

    private CoverageOptions coverageOptions;

//    private CoverageTypePremium coverageTypePremium;

    private double premium;

    private double levies;

    private double totalPremium;
}
