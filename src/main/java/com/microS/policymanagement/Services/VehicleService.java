package com.microS.policymanagement.Services;


import com.microS.policymanagement.DTO.UniversalResponse;
import com.microS.policymanagement.DTO.VehicleDTO;
import com.microS.policymanagement.Repository.VehicleRepository;
import com.microS.policymanagement.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public UniversalResponse<List<VehicleDTO>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleDTO> vehicleDTOs = vehicles.stream().map(this::convertToDTO).collect(Collectors.toList());
        return UniversalResponse.<List<VehicleDTO>>builder()
                .status(200)
                .message("Success")
                .data(String.valueOf(vehicleDTOs))
                .build();
    }

    public UniversalResponse<VehicleDTO> getVehicleById(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return vehicle.map(v -> {
            VehicleDTO vehicleDTO = convertToDTO(v);
            return UniversalResponse.<VehicleDTO>builder()
                    .status(200)
                    .message("Success")
                    .data(String.valueOf(vehicleDTO))
                    .build();
        }).orElseGet(() -> UniversalResponse.<VehicleDTO>builder()
                .status(404)
                .message("Vehicle not found")
                .build());
    }

    public UniversalResponse<Void> saveVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = convertToEntity(vehicleDTO);
        vehicleRepository.save(vehicle);
        return UniversalResponse.<Void>builder()
                .status(200)
                .message("Vehicle saved successfully")
                .data(String.valueOf(vehicleDTO))
                .build();
    }

    public UniversalResponse<Void> deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
        return UniversalResponse.<Void>builder()
                .status(200)
                .message("Vehicle deleted successfully")
                .build();
    }

    private VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setMake(vehicle.getMake());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setPlateNumber(vehicle.getPlateNumber());
        vehicleDTO.setValue(vehicle.getValue());
        vehicleDTO.setManufactureYear(vehicle.getManufactureYear());
        return vehicleDTO;
    }

    private Vehicle convertToEntity(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDTO.getId());
        vehicle.setMake(vehicleDTO.getMake());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setPlateNumber(vehicleDTO.getPlateNumber());
        vehicle.setValue(vehicleDTO.getValue());
        vehicle.setManufactureYear(vehicleDTO.getManufactureYear());
        return vehicle;
    }

}