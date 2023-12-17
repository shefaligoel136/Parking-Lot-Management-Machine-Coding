import Controllers.TicketController;
import Models.Gate;
import Models.ParkingLot;
import Models.ParkingSpot;
import Models.VehicleType;
import Repository.GateRepository;
import Repository.ParkingLotRepository;
import Repository.TicketRepository;
import Repository.VehicleRepository;
import Services.TicketService;
import Strategies.SpotAssignmentStrategy.RandomSpotAssignmentStrategy;
import Strategies.SpotAssignmentStrategy.SpotAssignmentStrategy;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        GateRepository gateRepository = new GateRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        TicketRepository ticketRepository = new TicketRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();

        SpotAssignmentStrategy spotAssignmentStrategy = new RandomSpotAssignmentStrategy();

        TicketService ticketService = new TicketService(gateRepository,vehicleRepository,spotAssignmentStrategy,ticketRepository,parkingLotRepository);

        TicketController ticketController = new TicketController(ticketService);

        System.out.println("APPLICATION STARTED!");

    }
}