package com.microS.policymanagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoverageOptionDTO {
    private Long id;
    private String name;
    private boolean geographicalLimit;
    private boolean personalInjury;
    private boolean lossOfPersonalEffects;
    private boolean passengerLiability;
    private boolean theftFireVehicleDamage;
    private boolean thirdPartyPersonsInjury;
}
