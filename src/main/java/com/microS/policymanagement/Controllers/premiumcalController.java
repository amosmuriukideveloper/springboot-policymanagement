package com.microS.policymanagement.Controllers;


import com.microS.policymanagement.Services.premiumcal;
import com.microS.policymanagement.models.CoverageOptions;
import com.microS.policymanagement.models.CoverageType;
import com.microS.policymanagement.models.Policy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/premium")
public class premiumcalController {

    private final premiumcal premiumCalculationService;

    public premiumcalController(premiumcal premiumCalculationService) {
        this.premiumCalculationService = premiumCalculationService;
    }

    @PostMapping("/cal")
    public ResponseEntity<Double> calculatePremiumForPolicy(@RequestBody Policy policy, @RequestParam CoverageType coverageType) {
        double premium = premiumCalculationService.calculatePremium(policy, CoverageType.valueOf(String.valueOf(coverageType)), policy.getVehicle());
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
