package com.microS.policymanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyHolderDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dob;
    private int nationalId;
    private String emailAddress;
    private String kra;
}
