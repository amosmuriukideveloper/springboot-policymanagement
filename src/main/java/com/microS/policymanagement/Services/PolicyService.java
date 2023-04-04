package com.microS.policymanagement.Services;

import com.microS.policymanagement.Exceptions.PolicyNotFoundException;
import com.microS.policymanagement.Repository.PolicyRepository;
import com.microS.policymanagement.models.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {
    private final PolicyRepository policyRepository;


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
