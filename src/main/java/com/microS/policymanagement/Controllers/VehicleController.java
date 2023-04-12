package com.microS.policymanagement.Controllers;


import com.microS.policymanagement.DTO.UniversalResponse;
import com.microS.policymanagement.DTO.VehicleDTO;
import com.microS.policymanagement.Services.VehicleService;
import com.microS.policymanagement.models.Vehicle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/getAll")
    public ResponseEntity<UniversalResponse<List<VehicleDTO>>> getAllVehicles() {
        UniversalResponse<List<VehicleDTO>> response = vehicleService.getAllVehicles();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UniversalResponse<VehicleDTO>> getVehicleById(@PathVariable("id") Long id) {
        UniversalResponse<VehicleDTO> response = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<UniversalResponse<Void>> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        UniversalResponse<Void> response = vehicleService.saveVehicle(vehicleDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UniversalResponse<Void>> deleteVehicle(@PathVariable("id") Long id) {
        UniversalResponse<Void> response = vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(response);
    }
}
