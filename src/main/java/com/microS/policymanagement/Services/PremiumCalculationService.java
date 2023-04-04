//package com.microS.policymanagement.Services;
//
//import com.microS.policymanagement.models.*;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Stream;
//
//@Service
//public class PremiumCalculationService {
//
//    private static final double BASE_COEFFICIENT = 0.07;
//    private static final double EXCESS_PROTECTOR_LEVY = 1500.0;
//    private static final double TERRORISM_LEVY = 500.0;
//    private static final double COURTESY_CAR_LEVY = 1000.0;
//    private final Map<String, Double> makeModelCoefficients = new HashMap<>();
//
////    public PremiumCalculationService() {
////        loadMakeModelCoefficients();
////    }
////
////    private void loadMakeModelCoefficients() {
////        try (Stream<String> stream = Files.lines(Paths.get("make_model_coefficients.csv"))) {
////            stream.forEach(line -> {
////                String[] parts = line.split(",");
////                makeModelCoefficients.put(parts[0], Double.parseDouble(parts[1]));
////            });
////        } catch (IOException e) {
////            throw new RuntimeException("Error loading make and model coefficients", e);
////        }
////    }
//
//    public double calculatePremiumForPolicy(Policy policy) {
//        double basePremium = calculateBasePremium(policy);
//        double coverageOptionsPremium = calculateCoverageOptionsPremium(policy.getCoverageOptions(), basePremium, policy.getCoverageType());
//        double leviesPremium = calculateLeviesPremium(policy);
//
//        return basePremium + coverageOptionsPremium + leviesPremium;
//    }
//
////    public double calculatePremiumForPolicyHolder(PolicyHolder policyHolder) {
////        double basePremium = calculateBasePremiumForPolicyHolder(policyHolder);
////        double loyaltyDiscountPremium = calculateLoyaltyDiscountPremium(policyHolder);
////        double ageDiscountPremium = calculateAgeDiscountPremium(policyHolder);
////        double genderLoadingPremium = calculateGenderLoadingPremium(policyHolder);
////
////        return basePremium - loyaltyDiscountPremium - ageDiscountPremium + genderLoadingPremium;
////    }
//
//    private double calculateBasePremium(Policy policy) {
//        Vehicle vehicle = policy.getVehicle();
//        double basePremium = vehicle.getValue() * BASE_COEFFICIENT;
//
//        String makeAndModel = vehicle.getMake() + " " + vehicle.getModel();
//        double makeModelCoefficient = makeModelCoefficients.getOrDefault(makeAndModel, 0.05);
//        basePremium += (basePremium * makeModelCoefficient);
//
//        int ageOfVehicle = LocalDate.now().getYear() - vehicle.getManufactureYear();
//        if (ageOfVehicle <= 5) {
//            basePremium *= 1.2; // 20% increase for vehicles 5 years old or less
//        } else if (ageOfVehicle > 5 && ageOfVehicle <= 10) {
//            basePremium *= 0.9; // 10% discount for vehicles between 5 and 10 years old
//        } else {
//            basePremium *= 0.8; // 20% discount for vehicles over 10 years old
//        }
//
//        return basePremium;
//    }
//
//    private double calculateCoverageOptionsPremium(List<CoverageOptions> coverageOptions, double basePremium, CoverageType coverageType) {
//        List<CoverageTypePremium> coverageTypePremiums = new ArrayList<>();
//
//
//
//            if (coverageType == CoverageType.GOLD) {
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.FIRE, 0.05));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THEFT, 0.05));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.FLOOD, 0.1));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.EARTHQUAKE, 0.15));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.GEOGRAPHICAL_LIMIT, 1.0));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.PERSONAL_INJURY, 0.02));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.LOSS_OF_PERSONAL_EFFECTS, 0.01));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.PASSENGER_LIABILITY, 0.01));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THEFT_FIRE_VEHICLE_DAMAGE, 0.1));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THIRD_PARTY_LIABILITY, 0.05));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THIRD_PARTY_PERSONS_INJURY, 0.01));
//            } else if (coverageType == CoverageType.SILVER) {
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.FIRE, 0.03));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THEFT, 0.03));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.FLOOD, 0.05));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.EARTHQUAKE, 0.1));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.GEOGRAPHICAL_LIMIT, 1.0));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.PERSONAL_INJURY, 0.01));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.LOSS_OF_PERSONAL_EFFECTS, 0.005));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.PASSENGER_LIABILITY, 0.005));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THEFT_FIRE_VEHICLE_DAMAGE, 0.05));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THIRD_PARTY_LIABILITY, 0.025));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THIRD_PARTY_PERSONS_INJURY, 0.005));
//            } else if (coverageType == CoverageType.BRONZE) {
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.FIRE, 0.01));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THEFT, 0.01));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.FLOOD, 0.02));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.EARTHQUAKE, 0.05));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.GEOGRAPHICAL_LIMIT, 1.0));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.PERSONAL_INJURY, 0.005));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.LOSS_OF_PERSONAL_EFFECTS, 0.0025));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.PASSENGER_LIABILITY, 0.0025));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THEFT_FIRE_VEHICLE_DAMAGE, 0.02));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THIRD_PARTY_LIABILITY, 0.01));
//                coverageTypePremiums.add(new CoverageTypePremium(CoverageOptions.THIRD_PARTY_PERSONS_INJURY, 0.0025));
//            }
//
//     else {
//        throw new IllegalArgumentException("Invalid coverage type provided");
//    }
//
//
//        double coverageOptionsPremium = 0.0;
//        for (CoverageOptions option : coverageOptions) {
//            for (CoverageTypePremium coverageTypePremium : coverageTypePremiums) {
//                if (coverageTypePremium.getCoverageType() == option) {
//                    coverageOptionsPremium += basePremium * coverageTypePremium.getCoefficient();
//                }
//            }
//        }
//
//
//        return coverageOptionsPremium;
//    }
//
//    private double calculateLeviesPremium(Policy policy) {
//        double leviesPremium = 0.0;
//        leviesPremium += EXCESS_PROTECTOR_LEVY;
//        leviesPremium += TERRORISM_LEVY;
//        if (policy.isIncludeCourtesyCar()) {
//            leviesPremium += COURTESY_CAR_LEVY;
//        }
//        return leviesPremium;
//    }
//
////    private double calculateBasePremiumForPolicyHolder(PolicyHolder policyHolder) {
////        double basePremium = 0.0;
////        basePremium += policyHolder.getAge() * 10;
////        basePremium += policyHolder.getDrivingExperience() * 5;
////        basePremium += policyHolder.getAnnualMileage() / 10000 * 5;
////        basePremium += policyHolder.getOccupationClass().getPremiumFactor() * 100;
////        return basePremium;
////    }
////
////    private double calculateLoyaltyDiscountPremium(PolicyHolder policyHolder) {
////        int yearsAsCustomer = policyHolder.getYearsAsCustomer();
////        if (yearsAsCustomer >= 5) {
////            return 0.1 * calculateBasePremiumForPolicyHolder(policyHolder);
////        } else if (yearsAsCustomer >= 3) {
////            return 0.05 * calculateBasePremiumForPolicyHolder(policyHolder);
////        } else {
////            return 0.0;
////        }
////    }
////
////    private double calculateAgeDiscountPremium(PolicyHolder policyHolder) {
////        int age = policyHolder.getAge();
////        if (age >= 60) {
////            return 0.2 * calculateBasePremiumForPolicyHolder(policyHolder);
////        } else if (age >= 50) {
////            return 0.1 * calculateBasePremiumForPolicyHolder(policyHolder);
////        } else {
////            return 0.0;
////        }
////    }
////
////    private double calculateGenderLoadingPremium(PolicyHolder policyHolder) {
////        Gender gender = policyHolder.getGender();
////        if (gender.equals(Gender.MALE)) {
////            return 0.1 * calculateBasePremiumForPolicyHolder(policyHolder);
////        } else {
////            return 0.0;
////        }
////    }
//
//}
//
//
