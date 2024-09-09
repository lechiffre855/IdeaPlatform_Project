package org.test;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.test.model.Ticket;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        String jarPath = Main.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        File file = new File(jarPath);
        File defaultFile = new File(file.getParentFile(), "tickets.json");
        String defaultPath = defaultFile.getAbsolutePath();

        String defaultOriginName = "Владивосток";
        String defaultDestinationName = "Тель-Авив";

        FileReader fileReader = new FileReader(defaultPath);
        String fileText = fileReader.readFile();

        TicketParser ticketParser = new TicketParser();
        Ticket[] tickets = ticketParser.parseTicket(fileText);

        TicketCalculator ticketCalculator = new TicketCalculator();

        String minFlightTimeByEveryCarrierText
                = ticketCalculator.getMinFlightTimeByEveryCarrier(tickets, defaultOriginName, defaultDestinationName);

        String averagePriceAndMedianPriceByDirectionText
                = ticketCalculator.getAveragePriceAndMedianPriceByDirection(tickets, defaultOriginName, defaultDestinationName);

        System.out.println(minFlightTimeByEveryCarrierText + "\n" + averagePriceAndMedianPriceByDirectionText);
    }
}