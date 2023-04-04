package com.microS.policymanagement.Repository;

import com.microS.policymanagement.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
