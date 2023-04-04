package com.microS.policymanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDTO {
    private Long id;

    private String policyNumber;

    private LocalDate startDate;

    private LocalDate endDate;

    private VehicleDTO vehicle;

    private PolicyHolderDTO policyHolder;

    private String coverageType;

    private List<String> coverageOptions;



}
