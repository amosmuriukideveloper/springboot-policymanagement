package com.microS.policymanagement.Controllers;

import com.microS.policymanagement.Exceptions.PolicyNotFoundException;
import com.microS.policymanagement.Services.PolicyService;
import com.microS.policymanagement.models.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("policies")
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Policy>> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @GetMapping("/getbyId/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) throws PolicyNotFoundException {
        Policy policy = policyService.getPolicyById(id);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        Policy createdPolicy = policyService.createPolicy(policy);
        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy policy) throws PolicyNotFoundException {
        Policy updatedPolicy = policyService.updatePolicy(id, policy);
        return new ResponseEntity<>(updatedPolicy, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) throws PolicyNotFoundException {
        policyService.deletePolicy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
