package com.microS.policymanagement.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
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




    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonBackReference // Use JsonBackReference to avoid circular reference

    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;



    public boolean isIncludeCourtesyCar() {
        return true;
    }
    public void addPolicy(Vehicle vehicle) {
        this.vehicle = vehicle;
        vehicle.getPolicies().add(this);
    }

    public void removePolicy() {
        if (vehicle != null) {
            vehicle.getPolicies().remove(this);
            vehicle = null;
        }
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


    // Helper method to set Vehicle entity and handle cascade
    public void setVehicle(Vehicle vehicle) {
        if (this.vehicle != null) {
            this.vehicle.getPolicies().remove(this);
        }
        this.vehicle = vehicle;
        if (vehicle != null) {
            vehicle.getPolicies().add(this);
        }
    }

    // Helper method to generate policy number with custom format
    @PrePersist
    public void generatePolicyNumber() {
        // Implement your custom policy number generation logic here
        policyNumber = "POL-" + id ;
    }


}



