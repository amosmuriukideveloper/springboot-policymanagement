package com.microS.policymanagement.Services;

import com.microS.policymanagement.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class PremiumCalculator {


    private static final double BASE_COEFFICIENT = 0.07;
    private static final double EXCESS_PROTECTOR_LEVY = 1500.0;
    private static final double TERRORISM_LEVY = 500.0;
    private static final double COURTESY_CAR_LEVY = 1000.0;

    // Coefficients for different policy types based on coverage type
    private static final double GOLD_COEFFICIENT_COMPREHENSIVE = 1.1;
    private static final double SILVER_COEFFICIENT_COMPREHENSIVE = 1.05;
    private static final double BRONZE_COEFFICIENT_COMPREHENSIVE = 1.02;

    private static final double GOLD_COEFFICIENT_THIRD_PARTY = 0.12;
    private static final double SILVER_COEFFICIENT_THIRD_PARTY = 0.11;
    private static final double BRONZE_COEFFICIENT_THIRD_PARTY = 0.105;

    private static final double GOLD_COEFFICIENT_ACT_ONLY_COVER = 0.13;
    private static final double SILVER_COEFFICIENT_ACT_ONLY_COVER = 0.12;
    private static final double BRONZE_COEFFICIENT_ACT_ONLY_COVER = 0.11;

    private static final double GOLD_COEFFICIENT_THIRD_PARTY_FIRE_AND_THEFT = 0.125;
    private static final double SILVER_COEFFICIENT_THIRD_PARTY_FIRE_AND_THEFT = 0.115;
    private static final double BRONZE_COEFFICIENT_THIRD_PARTY_FIRE_AND_THEFT = 0.11;


    private final Map<String, Double> makeModelCoefficients = new HashMap<>();
    private final Map<PolicyType, Map<CoverageOptions, Double>> policyCoefficients = new HashMap<>();

    public PremiumCalculator() {
        policyCoefficients.put(PolicyType.COMPREHENSIVE, createComprehensivePolicyCoefficients());
        policyCoefficients.put(PolicyType.THIRD_PARTY, createThirdPartyPolicyCoefficients());
        policyCoefficients.put(PolicyType.ACT_ONLY_COVER, createActOnlyCoverPolicyCoefficients());
        policyCoefficients.put(PolicyType.THIRD_PARTY_FIRE_AND_THEFT, createThirdPartyFireAndTheftPolicyCoefficients());
        populateMakeModelCoefficients();
    }

    private Map<CoverageOptions, Double> createComprehensivePolicyCoefficients() {
        Map<CoverageOptions, Double> coefficients = new HashMap<>();


        coefficients.put(CoverageOptions.FIRE, 0.1);
        coefficients.put(CoverageOptions.THEFT, 0.05);
        coefficients.put(CoverageOptions.THIRD_PARTY_LIABILITY, 0.015);
        coefficients.put(CoverageOptions.FLOOD, 0.001);
        coefficients.put(CoverageOptions.EARTHQUAKE, 0.1);
        coefficients.put(CoverageOptions.LOSS_OF_PERSONAL_EFFECTS, 0.05);
        coefficients.put(CoverageOptions.GEOGRAPHICAL_LIMIT, 0.005);
        coefficients.put(CoverageOptions.PERSONAL_INJURY, 0.2);
        coefficients.put(CoverageOptions.PASSENGER_LIABILITY, 0.1);
        coefficients.put(CoverageOptions.THEFT_FIRE_VEHICLE_DAMAGE, 0.15);
        coefficients.put(CoverageOptions.THIRD_PARTY_PERSONS_INJURY, 0.02);

        return coefficients;
    }
    private Map<CoverageOptions, Double> createThirdPartyPolicyCoefficients() {
        Map<CoverageOptions, Double> coefficients = new HashMap<>();
        coefficients.put(CoverageOptions.THIRD_PARTY_LIABILITY, 0.01);
        return coefficients;
    }

    private Map<CoverageOptions, Double> createActOnlyCoverPolicyCoefficients() {
        Map<CoverageOptions, Double> coefficients = new HashMap<>();
        coefficients.put(CoverageOptions.THIRD_PARTY_LIABILITY, 0.005);
        return coefficients;
    }

    private Map<CoverageOptions, Double> createThirdPartyFireAndTheftPolicyCoefficients() {
        Map<CoverageOptions, Double> coefficients = new HashMap<>();
        coefficients.put(CoverageOptions.THIRD_PARTY_LIABILITY, 0.01);
        coefficients.put(CoverageOptions.FIRE, 0.05);
        coefficients.put(CoverageOptions.THEFT, 0.05);
        return coefficients;
    }


    private void populateMakeModelCoefficients() {
        makeModelCoefficients.put("Toyota Camry", 0.001);
        makeModelCoefficients.put("Honda Civic", 0.002);
        makeModelCoefficients.put("Ford Focus", 0.003);
        makeModelCoefficients.put("Nissan Altima", 0.011);
        makeModelCoefficients.put("Chevrolet Malibu", 0.004);
    }

    public double calculatePremium(String makeModel, int manufactureYear, Policy policy, CoverageType coverageType, PolicyType policyType, Set<CoverageOptions> coverageOptions) {
        double basePremium = calculateBasePremium(manufactureYear, makeModel, policy);
        double policyCoefficient = calculatePolicyCoefficient(policyType, coverageOptions);
        double premium = basePremium * policyCoefficient;
        premium += getBaseCoefficient(policyType, coverageType);
        premium += calculateLeviesPremium(policy);

        return premium;

    }

    private double calculateBasePremium(int manufactureYear, String makeModel, Policy policy) {
        double makeModelCoefficient = makeModelCoefficients.getOrDefault(makeModel, 0.05);
        Vehicle vehicle = policy.getVehicle();
        double basePremium = vehicle.getValue() * BASE_COEFFICIENT;
        basePremium += (basePremium * makeModelCoefficient);
        int ageOfVehicle = LocalDate.now().getYear() - manufactureYear;
        if (ageOfVehicle <= 5) {
            basePremium *= 1.2; // 20% increase for vehicles 5 years old or less
        } else if (ageOfVehicle > 5 && ageOfVehicle <= 10) {
            basePremium *= 0.9; // 10% discount for vehicles between 5 and 10 years old
        } else if (ageOfVehicle > 10) {
            basePremium *= 0.8; // 20% discount for vehicles over 10 years old
            // Apply additional increase for vehicles older than 10 years
            basePremium *= 1.1; // 10% increase for vehicles older than 10 years
        }


        return basePremium;
    }




    private double calculatePolicyCoefficient(PolicyType policyType, Set<CoverageOptions> coverageOptions) {
        double policyCoefficient = 0.5;

        if (policyType == null) {
            // Handle the case when policyType is null
            throw new IllegalArgumentException("policyType cannot be null");
        }

        Map<CoverageOptions, Double> policyCoefficientsForType = policyCoefficients.get(policyType);

        // Initialize policy coefficients based on policy type
        switch (policyType) {
            case COMPREHENSIVE:
                policyCoefficientsForType = policyCoefficients.get(PolicyType.COMPREHENSIVE);
                break;
            case THIRD_PARTY:
                policyCoefficientsForType = policyCoefficients.get(PolicyType.THIRD_PARTY);
                break;
            case ACT_ONLY_COVER:
                policyCoefficientsForType = policyCoefficients.get(PolicyType.ACT_ONLY_COVER);
                break;
            case THIRD_PARTY_FIRE_AND_THEFT:
                policyCoefficientsForType = policyCoefficients.get(PolicyType.THIRD_PARTY_FIRE_AND_THEFT);
                break;
            default:
                // Handle default case, if needed
                break;
        }

        // Calculate policy coefficient based on coverage options
        for (CoverageOptions coverageOption : coverageOptions) {
            Double coverageCoefficient = policyCoefficientsForType.get(coverageOption);
            if (coverageCoefficient != null) {
                policyCoefficient += coverageCoefficient;
            }
        }

        return policyCoefficient;

    }

    private double getBaseCoefficient(PolicyType policyType, CoverageType coverageType) {
        double baseCoefficient = 0.0;
        switch (policyType) {
            case COMPREHENSIVE:
                switch (coverageType) {
                    case GOLD:
                        baseCoefficient *= GOLD_COEFFICIENT_COMPREHENSIVE;
                        break;
                    case SILVER:
                        baseCoefficient *= SILVER_COEFFICIENT_COMPREHENSIVE;
                        break;
                    case BRONZE:
                        baseCoefficient *= BRONZE_COEFFICIENT_COMPREHENSIVE;
                        break;
                }
                break;
            case THIRD_PARTY:
                switch (coverageType) {
                    case GOLD:
                        baseCoefficient *= GOLD_COEFFICIENT_THIRD_PARTY;
                        break;
                    case SILVER:
                        baseCoefficient *= SILVER_COEFFICIENT_THIRD_PARTY;
                        break;
                    case BRONZE:
                        baseCoefficient *= BRONZE_COEFFICIENT_THIRD_PARTY;
                        break;
                }
                break;
            case ACT_ONLY_COVER:
                switch (coverageType) {
                    case GOLD:
                        baseCoefficient *= GOLD_COEFFICIENT_ACT_ONLY_COVER;
                        break;
                    case SILVER:
                        baseCoefficient *= SILVER_COEFFICIENT_ACT_ONLY_COVER;
                        break;
                    case BRONZE:
                        baseCoefficient *= BRONZE_COEFFICIENT_ACT_ONLY_COVER;
                        break;
                }
                break;
            case THIRD_PARTY_FIRE_AND_THEFT:
                switch (coverageType) {
                    case GOLD:
                        baseCoefficient *= GOLD_COEFFICIENT_THIRD_PARTY_FIRE_AND_THEFT;
                        break;
                    case SILVER:
                        baseCoefficient *= SILVER_COEFFICIENT_THIRD_PARTY_FIRE_AND_THEFT;
                        break;
                    case BRONZE:
                        baseCoefficient *= BRONZE_COEFFICIENT_THIRD_PARTY_FIRE_AND_THEFT;
                        break;
                }
                break;
        }
        return baseCoefficient;
    }


    private double calculateLeviesPremium(Policy policy) {
        double leviesPremium = 0.0;
        leviesPremium += EXCESS_PROTECTOR_LEVY;
        leviesPremium += TERRORISM_LEVY;
        if (policy.isIncludeCourtesyCar()) {
            leviesPremium += COURTESY_CAR_LEVY;
        }
        return leviesPremium;
    }


                }


