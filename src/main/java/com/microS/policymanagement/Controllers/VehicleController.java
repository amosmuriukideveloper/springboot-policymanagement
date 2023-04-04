package com.microS.policymanagement.Controllers;


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
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<VehicleDTO> vehicleDTOs = vehicles.stream()
                .map(vehicle -> {
                    VehicleDTO vehicleDTO = new VehicleDTO();
                    BeanUtils.copyProperties(vehicle, vehicleDTO);
                    return vehicleDTO;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicleDTOs);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable("id") Long id) {
        Optional<Vehicle> optionalVehicle = vehicleService.getVehicleById(id);
        if (optionalVehicle.isPresent()) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            BeanUtils.copyProperties(optionalVehicle.get(), vehicleDTO);
            return ResponseEntity.ok(vehicleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<VehicleDTO> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleDTO, vehicle);
        vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(vehicleDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
