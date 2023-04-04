package com.microS.policymanagement.Services;

import com.microS.policymanagement.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.microS.policymanagement.models.PolicyType.COMPREHENSIVE;

@Service
public class premiumcal {


    private static final double BASE_COEFFICIENT = 0.07;
    private static final double EXCESS_PROTECTOR_LEVY = 1500.0;
    private static final double TERRORISM_LEVY = 500.0;
    private static final double COURTESY_CAR_LEVY = 1000.0;
    private final Map<String, Double> makeModelCoefficients = new HashMap<>();

    // Constants for coverage types
    private static final double GOLD_COVERAGE_MIN_PREMIUM = 100000.0;
    private static final double GOLD_COVERAGE_MAX_PREMIUM = 250000.0;
    private static final double SILVER_COVERAGE_MIN_PREMIUM = 75000.0;
    private static final double SILVER_COVERAGE_MAX_PREMIUM = 150000.0;
    private static final double BRONZE_COVERAGE_MIN_PREMIUM = 50000.0;
    private static final double BRONZE_COVERAGE_MAX_PREMIUM = 100000.0;

    // Coefficients for each coverage option
    private final Map<String, Map<String, Double>> policyCoefficients = new HashMap<>();



    public void PremiumCalculator() {
        Map<String, Double> comprehensiveCoefficients = new HashMap<>();

        // Adding coefficients for each coverage option
        comprehensiveCoefficients.put("FIRE", 0.1);
        comprehensiveCoefficients.put("THEFT", 0.05);
        comprehensiveCoefficients.put("THIRD_PARTY_LIABILITY", 0.15);
        comprehensiveCoefficients.put("COURTESY_CAR", 0.05);
        comprehensiveCoefficients.put("FLOOD", 0.1);
        comprehensiveCoefficients.put("EARTHQUAKE", 0.1);
        comprehensiveCoefficients.put("LOSS_OF_PERSONAL_EFFECTS", 0.05);
        comprehensiveCoefficients.put("GEOGRAPHICAL_LIMIT", 0.05);
        comprehensiveCoefficients.put("PERSONAL_INJURY", 0.2);
        comprehensiveCoefficients.put("PASSENGER_LIABILITY", 0.1);
        comprehensiveCoefficients.put("THEFT_FIRE_VEHICLE_DAMAGE", 0.15);
        comprehensiveCoefficients.put("THIRD_PARTY_PERSONS_INJURY", 0.2);
        policyCoefficients.put("COMPREHENSIVE", comprehensiveCoefficients);

        Map<String, Double> thirdPartyCoefficients = new HashMap<>();
        thirdPartyCoefficients.put("THIRD_PARTY_LIABILITY", 0.15);
        policyCoefficients.put("THIRD_PARTY", thirdPartyCoefficients);

        Map<String, Double> actOnlyCoefficients = new HashMap<>();
        actOnlyCoefficients.put("THIRD_PARTY_LIABILITY", 0.15);
        policyCoefficients.put("ACT_ONLY_COVER", actOnlyCoefficients);

        Map<String, Double> thirdPartyFireAndTheftCoefficients = new HashMap<>();
        thirdPartyFireAndTheftCoefficients.put("FIRE", 0.1);
        thirdPartyFireAndTheftCoefficients.put("THEFT", 0.05);
        thirdPartyFireAndTheftCoefficients.put("THIRD_PARTY_LIABILITY", 0.15);
        thirdPartyFireAndTheftCoefficients.put("THEFT_FIRE_VEHICLE_DAMAGE", 0.15);
        policyCoefficients.put("THIRD_PARTY_FIRE_AND_THEFT", thirdPartyFireAndTheftCoefficients);

        // Adding coefficients for each make and model of car
        makeModelCoefficients.put("TOYOTA_CAMRY", 1.0);
        makeModelCoefficients.put("HONDA_ACCORD", 1.2);
        makeModelCoefficients.put("NISSAN_ALTIMA", 1.1);
        makeModelCoefficients.put("FORD_MUSTANG", 1.3);
        makeModelCoefficients.put("CHEVROLET_CORVETTE", 1.5);
        makeModelCoefficients.put("DODGE_CHARGER", 1.4);

    }

    public double calculatePremium(Policy policy, CoverageType coverageType, Vehicle vehicle) {
        double premium = 0.0;

        // Check that the coverage type parameter is not null or empty
        if (coverageType == null || coverageType.toString().isEmpty()) {
            throw new IllegalArgumentException("Coverage type parameter is missing or empty");
        }

        // Map the coverage type parameter to an enum value
        CoverageType coverageTypeEnum = CoverageType.valueOf(String.valueOf(coverageType));

        // Calculate the base premium
        premium = vehicle.getValue() * BASE_COEFFICIENT;


        String makeAndModel = vehicle.getMake() + " " + vehicle.getModel();
        double makeModelCoefficient = makeModelCoefficients.getOrDefault(makeAndModel, 0.05);
        premium += (premium * makeModelCoefficient);

        int ageOfVehicle = LocalDate.now().getYear() - vehicle.getManufactureYear();
        if (ageOfVehicle <= 5) {
            premium *= 1.2; // 20% increase for vehicles 5 years old or less
        } else if (ageOfVehicle > 5 && ageOfVehicle <= 10) {
            premium *= 0.9; // 10% discount for vehicles between 5 and 10 years old
        } else {
            premium *= 0.8; // 20% discount for vehicles over 10 years old
        }

        double coverageMinPremium = 0.0;
        double coverageMaxPremium = 0.0;
        switch (coverageType) {
            case GOLD:
                coverageMinPremium = GOLD_COVERAGE_MIN_PREMIUM;
                coverageMaxPremium = GOLD_COVERAGE_MAX_PREMIUM;
                break;
            case SILVER:
                coverageMinPremium = SILVER_COVERAGE_MIN_PREMIUM;
                coverageMaxPremium = SILVER_COVERAGE_MAX_PREMIUM;
                break;
            case BRONZE:
                coverageMinPremium = BRONZE_COVERAGE_MIN_PREMIUM;
                coverageMaxPremium = BRONZE_COVERAGE_MAX_PREMIUM;
                break;
            default:
                throw new IllegalArgumentException("Invalid coverage type: " + coverageType);
        }


        Map<String, Double> policyTypeCoefficients = policyCoefficients.get("COMPREHENSIVE");
        System.out.print("tytuyyuggh"+policyTypeCoefficients.get("FIRE"));
        if (policyTypeCoefficients != null) {
//            System.out.print(policyCoefficients.get(policy));
            Double coverageCoefficient = policyTypeCoefficients.get(coverageTypeEnum.toString());
            if (coverageCoefficient != null) {
                double coveragePremium = (vehicle.getValue() * coverageCoefficient) - coverageMinPremium;
                if (coveragePremium < 0.0) {
                    coveragePremium = 0.0;
                } else if (coveragePremium > (coverageMaxPremium - coverageMinPremium)) {
                    coveragePremium = (coverageMaxPremium - coverageMinPremium);
                }
                premium += coverageMinPremium + coveragePremium;
            } else {
                throw new IllegalArgumentException("Coverage type coefficient not found for policy type: " + policy.getPolicyType() + ", coverage type: " + coverageTypeEnum);
            }
        } else {
            throw new IllegalArgumentException("Policy type coefficients not found for policy type: " + policy.getPolicyType());
        }




        // Add excess protector levy
        premium += EXCESS_PROTECTOR_LEVY;

        // Add terrorism levy
        premium += TERRORISM_LEVY;

        // Add courtesy car levy
        if (policy.isIncludeCourtesyCar()) {
            premium += COURTESY_CAR_LEVY;
        }

        return premium;
    }

}

