package com.microS.policymanagement.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_number", unique = true) // Make policy_number field unique
    private String policyNumber;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


    @JsonDeserialize(using = PolicyTypeDeserializer.class)
    public PolicyType policyType;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_holder")
    private PolicyHolder policyHolder;

    @Enumerated(EnumType.STRING)
    @Column(name = "coverage_type")
    private CoverageType coverageType;

    @ElementCollection
    @CollectionTable(name = "policy_coverage_options", joinColumns = @JoinColumn(name = "policy_id"))
    @Column(name = "coverage_option")
    private List<CoverageOptions> coverageOptions;


    // Retrieve Vehicle entity from persistence context

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;



    public boolean isIncludeCourtesyCar() {
        return true;
    }


    public boolean isExcessProtector() {
        return true;
    }

    public boolean isTerrorismCover() {
        return true;
    }

    public boolean isCourtesyCar() {
        return true;
    }




    // Helper method to generate policy number with custom format
    @PrePersist
    public void generatePolicyNumber() {
        // Implement your custom policy number generation logic here
        policyNumber = "POL-" + id ;
    }

    private int premium;


}



