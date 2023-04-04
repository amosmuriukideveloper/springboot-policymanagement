package com.microS.policymanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "policy_holder")
public class PolicyHolder {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "dob")
        private Date dob;

        @Column(name = "national_Id")
        private int nationalId;

        @Column(name = "email")
        private String email;

        @Column(name = "kra")
        private String kra;



}
