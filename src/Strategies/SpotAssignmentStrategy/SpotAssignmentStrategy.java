package Strategies.SpotAssignmentStrategy;

import Models.Gate;
import Models.ParkingLot;
import Models.ParkingSpot;
import Models.VehicleType;

import java.util.Optional;

public interface SpotAssignmentStrategy {
    Optional<ParkingSpot> findSpot(VehicleType vehicleType, ParkingLot parkingLot, Gate entryGate);
}
