package com.microS.policymanagement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "value")
    private double value;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "year_of_manufacture")
    private int manufactureYear;

    @OneToMany (mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Use JsonManagedReference to manage circular reference
    private List<Policy> policies = new ArrayList<>();

    public void addPolicy(Policy policy) {
        policies.add(policy);
        policy.setVehicle(this);
    }

    public void removePolicy(Policy policy) {
        policies.remove(policy);
        policy.setVehicle(null);
    }
}
