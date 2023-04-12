package com.microS.policymanagement.Controllers;

import com.microS.policymanagement.DTO.PolicyHolderDTO;
import com.microS.policymanagement.DTO.UniversalResponse;
import com.microS.policymanagement.Services.PolicyHolderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policyholders")
public class PolicyHolderController {
    @Autowired
    private PolicyHolderService policyHolderService;

    @PostMapping("/validateName")
    public ResponseEntity<UniversalResponse<Boolean>> validateNameAgainstNationalId(
            @RequestBody @Valid PolicyHolderDTO policyHolderDTO) {
        boolean isValid = policyHolderService.validateNameAgainstNationalId(policyHolderDTO);
        UniversalResponse<Boolean> response = new UniversalResponse<>();
        response.setStatus(200);
        response.setMessage("Success");
        response.setData(String.valueOf(isValid));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getall")
    public ResponseEntity<UniversalResponse<List<PolicyHolderDTO>>> getAllPolicyHolders() {
        UniversalResponse<List<PolicyHolderDTO>> response = policyHolderService.getAllPolicyHolders();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UniversalResponse<PolicyHolderDTO>> getPolicyHolderById(@PathVariable("id") Long id) {
        UniversalResponse<PolicyHolderDTO> response = policyHolderService.getPolicyHolderById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/savePolicyHolder")
    public ResponseEntity<UniversalResponse<PolicyHolderDTO>> savePolicyHolder(
            @RequestBody @Valid PolicyHolderDTO policyHolderDTO) {
        UniversalResponse<PolicyHolderDTO> response = policyHolderService.savePolicyHolder(policyHolderDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UniversalResponse<Void>> deletePolicyHolder(@PathVariable("id") Long id) {
        UniversalResponse<Void> response = policyHolderService.deletePolicyHolder(id);
        return ResponseEntity.ok(response);
    }
}
