package Repository;

import Models.Ticket;
import Models.Vehicle;

import java.util.Map;
import java.util.TreeMap;

public class TicketRepository {
    private Map<Long,Ticket> tickets = new TreeMap<>();
    Long lastSetId = 0L;
    public Ticket save(Ticket ticket){
        ticket.setId(lastSetId+1);
        lastSetId += 1;
        tickets.put(lastSetId,ticket);
        return ticket;
    }

}
