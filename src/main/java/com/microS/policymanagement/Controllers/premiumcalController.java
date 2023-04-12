package com.microS.policymanagement.Controllers;


import com.microS.policymanagement.Services.PremiumCalculator;
import com.microS.policymanagement.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/premium")
public class premiumcalController {


    private final PremiumCalculator premiumCalculator;

    public premiumcalController(PremiumCalculator premiumCalculator) {
        this.premiumCalculator = premiumCalculator;
    }

    @PostMapping("/cal")
    public ResponseEntity<Double> calculatePremiumForPolicy(@RequestBody Policy policy) {
        Vehicle vehicle = policy.getVehicle();
        Integer manufactureYear = vehicle.getManufactureYear();
        String makeModel = vehicle.getMake() + " " + vehicle.getModel(); // Concatenate make and model
        PolicyType policyType = policy.getPolicyType();
        CoverageType coverageType = policy.getCoverageType();
        List<CoverageOptions> coverageOptions = policy.getCoverageOptions(); // Change to List
        if (manufactureYear == null || makeModel == null || policyType == null || coverageOptions == null) {
            return ResponseEntity.badRequest().build();
        }

        Set<CoverageOptions> coverageOptionsSet = new HashSet<>(coverageOptions); // Convert to Set
        double premium = premiumCalculator.calculatePremium(makeModel, manufactureYear, policy, coverageType, policyType, coverageOptionsSet); // Use Set
        return ResponseEntity.ok(premium);
    }

    @GetMapping("/coverage-options")
    public ResponseEntity<List<String>> getCoverageOptions() {
        List<String> coverageOptions = Arrays.stream(CoverageOptions.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(coverageOptions);
    }

    @GetMapping("/coverage-types")
    public ResponseEntity<List<String>> getCoverageTypes() {
        List<String> coverageTypes = Arrays.stream(CoverageType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(coverageTypes);
    }
}
