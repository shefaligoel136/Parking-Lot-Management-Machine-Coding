package Services;

import Exceptions.InvalidGateException;
import Exceptions.NoAvailableParkingSpot;
import Exceptions.NoParkingLotFound;
import Models.*;
import Repository.*;
import Strategies.SpotAssignmentStrategy.SpotAssignmentStrategy;

import java.util.Date;
import java.util.Optional;

public class TicketService {

    private IGateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private SpotAssignmentStrategy spotAssignmentStrategy;

    private TicketRepository ticketRepository;
    private ParkingLotRepository parkingLotRepository;

    public TicketService(IGateRepository gateRepository, VehicleRepository vehicleRepository, SpotAssignmentStrategy spotAssignmentStrategy, TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository){
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.spotAssignmentStrategy = spotAssignmentStrategy;
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
    }
    public Ticket generateTicket(Long gateId, VehicleType vehicleType, String vehicleNumber) throws InvalidGateException, NoAvailableParkingSpot, NoParkingLotFound {

        /*
        *
        * 1. ParkingSpot - get parking spot from strategy.
        * 2. Vehicle - check in db, otherwise create
        * 3. Gate - get gate by gateId
        * 4. Operator - from gate
        *
        * */

        Optional<Gate> gateOptional = gateRepository.findGateById(gateId);
        if(gateOptional.isEmpty()){
            throw new InvalidGateException();
        }

        Gate gate = gateOptional.get();

        Operator operator = gate.getOperator();

        Optional<Vehicle> vehicleOptional = vehicleRepository.findVehicleByNumber(vehicleNumber);

        Vehicle vehicle;

        if(vehicleOptional.isEmpty()){
            vehicle = new Vehicle();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setVehicleType(vehicleType);
            vehicle = vehicleRepository.save(vehicle);
        }else{
            vehicle = vehicleOptional.get();
        }

        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.getParkingLotOfGate(gate);

        if(parkingLotOptional.isEmpty()){
            throw new NoParkingLotFound();
        }

        ParkingLot parkingLot = parkingLotOptional.get();

        Optional<ParkingSpot> parkingSpotOptional = spotAssignmentStrategy.findSpot(vehicleType,parkingLot,gate);

        if(parkingSpotOptional.isEmpty()){
            throw new NoAvailableParkingSpot();
        }

        ParkingSpot parkingSpot = parkingSpotOptional.get();

        Ticket ticket = new Ticket();

        ticket.setParkingSpot(parkingSpot);
        ticket.setGate(gate);
        ticket.setEntryTime(new Date());
        ticket.setVehicle(vehicle);
        ticket.setOperator(operator);

        return ticketRepository.save(ticket);
    }
}
