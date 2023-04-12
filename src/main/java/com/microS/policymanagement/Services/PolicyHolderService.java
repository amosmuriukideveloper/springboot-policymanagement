package com.microS.policymanagement.Services;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microS.policymanagement.DTO.PolicyHolderDTO;
import com.microS.policymanagement.DTO.UniversalResponse;
import com.microS.policymanagement.Repository.PolicyHolderRepository;
import com.microS.policymanagement.models.PolicyHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolicyHolderService {
    @Autowired
    private PolicyHolderRepository policyHolderRepository;

// method to validate the entered first name and lastname against their nationalId number
public boolean validateNameAgainstNationalId(PolicyHolderDTO policyHolderDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "https://iprs.go.ke/api/v1/verification";
        String apiKey = "YOUR_API_KEY";

        String request = "{\n" +
        "  \"id_number\": \"" + policyHolderDTO.getNationalId() + "\",\n" +
        "  \"first_name\": \"" + policyHolderDTO.getFirstName() + "\",\n" +
        "  \"last_name\": \"" + policyHolderDTO.getLastName() + "\"\n" +
        "}";

        headers.setBearerAuth(apiKey);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        String response = responseEntity.getBody();

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();

        boolean isValid = jsonResponse.get("valid").getAsBoolean();

        return isValid;
        }


    public UniversalResponse<List<PolicyHolderDTO>> getAllPolicyHolders() {
        List<PolicyHolder> policyHolders = policyHolderRepository.findAll();
        List<PolicyHolderDTO> policyHolderDTOs = policyHolders.stream()
                .map(policyHolder -> {
                    PolicyHolderDTO policyHolderDTO = new PolicyHolderDTO();
                    BeanUtils.copyProperties(policyHolder, policyHolderDTO);
                    return policyHolderDTO;
                })
                .collect(Collectors.toList());
        return UniversalResponse.<List<PolicyHolderDTO>>builder()
                .status(200)
                .message("Success")
                .data(String.valueOf(policyHolderDTOs))
                .build();
    }

    public UniversalResponse<PolicyHolderDTO> getPolicyHolderById(Long id) {
        Optional<PolicyHolder> policyHolder = policyHolderRepository.findById(id);
        if (policyHolder.isPresent()) {
            PolicyHolderDTO policyHolderDTO = new PolicyHolderDTO();
            BeanUtils.copyProperties(policyHolder.get(), policyHolderDTO);
            return UniversalResponse.<PolicyHolderDTO>builder()
                    .status(200)
                    .message("Success")
                    .data(String.valueOf(policyHolderDTO))
                    .build();
        } else {
            return UniversalResponse.<PolicyHolderDTO>builder()
                    .status(404)
                    .message("Policy Holder not found")
                    .build();
        }
    }

    public UniversalResponse<PolicyHolderDTO> savePolicyHolder(PolicyHolderDTO policyHolderDTO) {
        PolicyHolder policyHolder = new PolicyHolder();
        BeanUtils.copyProperties(policyHolderDTO, policyHolder);
        PolicyHolder savedPolicyHolder = policyHolderRepository.save(policyHolder);
        PolicyHolderDTO savedPolicyHolderDTO = new PolicyHolderDTO();
        BeanUtils.copyProperties(savedPolicyHolder, savedPolicyHolderDTO);
        return UniversalResponse.<PolicyHolderDTO>builder()
                .status(201)
                .message("Policy Holder created successfully")
                .data(String.valueOf(savedPolicyHolderDTO))
                .build();
    }

    public UniversalResponse<Void> deletePolicyHolder(Long id) {
        Optional<PolicyHolder> policyHolder = policyHolderRepository.findById(id);
        if (policyHolder.isPresent()) {
            policyHolderRepository.deleteById(id);
            return UniversalResponse.<Void>builder()
                    .status(204)
                    .message("Policy Holder deleted successfully")
                    .build();
        } else {
            return UniversalResponse.<Void>builder()
                    .status(404)
                    .message("Policy Holder not found")
                    .build();
        }
    }
}

