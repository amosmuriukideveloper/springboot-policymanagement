package com.microS.policymanagement.Services;

import com.microS.policymanagement.Exceptions.PolicyNotFoundException;
import com.microS.policymanagement.Repository.PolicyRepository;
import com.microS.policymanagement.models.CoverageOptions;
import com.microS.policymanagement.models.CoverageType;
import com.microS.policymanagement.models.Policy;
import com.microS.policymanagement.models.PolicyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PolicyService {
    private final PolicyRepository policyRepository;

    @Autowired
    private PremiumCalculator premiumCalculationService;

    @Autowired
    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id) throws PolicyNotFoundException {
        return policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + id));
    }

    public Policy createPolicy(Policy policy) {

//        // Extract relevant information from Policy object
//        String makeModel = policy.getVehicle().getMake() + " " + policy.getVehicle().getModel();
//        int manufactureYear = policy.getVehicle().getManufactureYear();
//        CoverageType coverageType = policy.getCoverageType();
//        PolicyType policyType = policy.getPolicyType();
//        Set<CoverageOptions> coverageOptions = (Set<CoverageOptions>) policy.getCoverageOptions();
//
//        // Perform premium calculation using premium calculation service class
//        double premium = premiumCalculationService.calculatePremium(
//                makeModel,
//                manufactureYear,
//                coverageType,
//                policyType,
//                coverageOptions
//        );
//        policy.setPremium((int) premium);

        return policyRepository.save(policy);
    }

    public Policy updatePolicy(Long id, Policy policy) throws PolicyNotFoundException {
        Policy existingPolicy = getPolicyById(id);
        existingPolicy.setPolicyNumber(policy.getPolicyNumber());
        existingPolicy.setPolicyHolder(policy.getPolicyHolder());
        existingPolicy.setCoverageOptions(policy.getCoverageOptions());
        existingPolicy.setStartDate(policy.getStartDate());
        existingPolicy.setEndDate(policy.getEndDate());
        existingPolicy.setVehicle(policy.getVehicle());

        return policyRepository.save(existingPolicy);
    }

    public void deletePolicy(Long id) throws PolicyNotFoundException {
        Policy policy = getPolicyById(id);
        policyRepository.delete(policy);
    }
}
