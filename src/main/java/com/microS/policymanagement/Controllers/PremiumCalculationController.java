//package com.microS.policymanagement.Controllers;
//
//import com.microS.policymanagement.Services.PremiumCalculationService;
//import com.microS.policymanagement.models.CoverageOptions;
//import com.microS.policymanagement.models.CoverageType;
//import com.microS.policymanagement.models.Policy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/premium")
//public class PremiumCalculationController {
//
//    private final PremiumCalculationService premiumCalculationService;
//
//    @Autowired
//    public PremiumCalculationController(PremiumCalculationService premiumCalculationService) {
//        this.premiumCalculationService = premiumCalculationService;
//    }
//
//    @PostMapping("/calculate")
//    public ResponseEntity<Double> calculatePremiumForPolicy(@RequestBody Policy policy) {
//        double premium = premiumCalculationService.calculatePremiumForPolicy(policy);
//        return new ResponseEntity<>(premium, HttpStatus.OK);
//    }
//
//    @GetMapping("/coverageOptions")
//    public ResponseEntity<List<CoverageOptions>> getCoverageOptions() {
//        List<CoverageOptions> coverageOptions = List.of(CoverageOptions.values());
//        return new ResponseEntity<>(coverageOptions, HttpStatus.OK);
//    }
//
//    @GetMapping("/coverageType")
//    public ResponseEntity<List<CoverageType>> getCoverageType() {
//        List<CoverageType> coverageType = List.of(CoverageType.values());
//        return new ResponseEntity<>(coverageType, HttpStatus.OK);
//    }
//}
