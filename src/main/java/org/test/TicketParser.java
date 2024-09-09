package org.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.test.exception.BadJsonTicketException;
import org.test.model.Ticket;

import java.util.Map;

public class TicketParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public TicketParser() {
    }

    public Ticket[] parseTicket(String jsonTicket){

        if(jsonTicket == null || jsonTicket.isBlank()){
            throw new BadJsonTicketException("Json Ticket is null or empty!");
        }

        jsonTicket = jsonTicket.replace("\uFEFF", "");

        try {
            JsonNode ticketArrayNode = mapper.readTree(jsonTicket).get("tickets");
            return mapper.treeToValue(ticketArrayNode, Ticket[].class);
        } catch (JsonProcessingException e) {
            throw new BadJsonTicketException("Incorrect format of json ticket!", e);
        }
    }
}
