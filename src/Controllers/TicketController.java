package Controllers;

import DTOs.GenerateTicketRequestDTO;
import DTOs.GenerateTicketResponseDTO;
import DTOs.ResponseStatus;
import Exceptions.InvalidGateException;
import Exceptions.NoAvailableParkingSpot;
import Exceptions.NoParkingLotFound;
import Models.Gate;
import Models.Ticket;
import Models.Vehicle;
import Models.VehicleType;
import Services.TicketService;

public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public GenerateTicketResponseDTO generateTicket(GenerateTicketRequestDTO request) {

        String vehicleNumber = request.getVehicleNumber();
        VehicleType vehicleType= request.getVehicleType();
        Long gateId = request.getGateId();

        Ticket ticket = new Ticket();

        GenerateTicketResponseDTO responseDTO = new GenerateTicketResponseDTO();

        try{
            ticket = ticketService.generateTicket(gateId, vehicleType, vehicleNumber);
        }catch (InvalidGateException | NoAvailableParkingSpot | NoParkingLotFound e){
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
            return responseDTO;
        }


        responseDTO.setTicketId(ticket.getId());
        responseDTO.setOperatorName(ticket.getOperator().getName());
        responseDTO.setSpotNumber(ticket.getParkingSpot().getNumber());
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);

        return responseDTO;

    }
}
