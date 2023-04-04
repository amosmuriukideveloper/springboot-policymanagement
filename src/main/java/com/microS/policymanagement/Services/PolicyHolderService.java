//package com.microS.policymanagement.Services;
//
//
//import com.microS.policymanagement.DTO.PolicyHolderDTO;
//import com.microS.policymanagement.Repository.PolicyHolderRepository;
//import com.microS.policymanagement.models.PolicyHolder;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.http.HttpHeaders;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class PolicyHolderService {
//    @Autowired
//    private PolicyHolderRepository policyHolderRepository;
//
//    // method to validate the entered first name and lastname against their nationalId number
//    public boolean validateNameAgainstNationalId(PolicyHolderDTO policyHolderDTO) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth("YOUR_API_KEY");
//
//        String url = "https://api.youverify.co/api/v1/kyc/verifyid.json";
//
//        JsonObject request = new JsonObject();
//        request.addProperty("id_number", policyHolderDTO.getNationalId());
//        request.addProperty("first_name", policyHolderDTO.getFirstName());
//        request.addProperty("last_name", policyHolderDTO.getLastName());
//        request.addProperty("dob", policyHolderDTO.getDob().toString());
//
//        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
//        String response = responseEntity.getBody();
//
//        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
//
//        boolean isValid = jsonResponse.get("valid").getAsBoolean();
//
//        return isValid;
//    }
//
//
//    public List<PolicyHolderDTO> getAllPolicyHolders() {
//        List<PolicyHolder> policyHolders = policyHolderRepository.findAll();
//        return policyHolders.stream()
//                .map(policyHolder -> {
//                    PolicyHolderDTO policyHolderDTO = new PolicyHolderDTO();
//                    BeanUtils.copyProperties(policyHolder, policyHolderDTO);
//                    return policyHolderDTO;
//                })
//                .collect(Collectors.toList());
//    }
//
//    public Optional<PolicyHolderDTO> getPolicyHolderById(Long id) {
//        Optional<PolicyHolder> optionalPolicyHolder = policyHolderRepository.findById(id);
//        if (optionalPolicyHolder.isPresent()) {
//            PolicyHolderDTO policyHolderDTO = new PolicyHolderDTO();
//            BeanUtils.copyProperties(optionalPolicyHolder.get(), policyHolderDTO);
//            return Optional.of(policyHolderDTO);
//        } else {
//            return Optional.empty();
//        }
//    }
//
//    public PolicyHolderDTO savePolicyHolder(PolicyHolderDTO policyHolderDTO) {
//        if (validateNameAgainstNationalId(policyHolderDTO)) {
//            PolicyHolder policyHolder = new PolicyHolder();
//            BeanUtils.copyProperties(policyHolderDTO, policyHolder);
//            policyHolder = policyHolderRepository.save(policyHolder);
//            BeanUtils.copyProperties(policyHolder, policyHolderDTO);
//            return policyHolderDTO;
//        } else {
//            throw new IllegalArgumentException("Invalid name and national ID combination");
//        }
//    }
//
//    public void deletePolicyHolder(Long id) {
//        policyHolderRepository.deleteById(id);
//    }
//}
