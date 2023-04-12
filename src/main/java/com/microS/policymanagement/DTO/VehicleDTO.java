package com.microS.policymanagement.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

   private Long id;
    private String make;


    private String model;


    private double value;


    private String plateNumber;


    private int manufactureYear;
}
