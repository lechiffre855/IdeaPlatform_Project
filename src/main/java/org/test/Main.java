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

        Terminal terminal = TerminalBuilder.terminal();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();

        String jarPath = Main.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        File file = new File(jarPath);
        File defaultFile = new File(file.getParentFile(), "tickets.json");
        String defaultPath = defaultFile.getAbsolutePath();

        String path = lineReader.readLine("Введите путь к файлу:\n", null, defaultPath);

        String originName = lineReader.readLine("Введите город отправления:\n", null, "Владивосток");
        String destinationName = lineReader.readLine("Введите город назначения:\n", null, "Тель-Авив");

        FileReader fileReader = new FileReader(path);
        String fileText = fileReader.readFile();

        TicketParser ticketParser = new TicketParser();
        Ticket[] tickets = ticketParser.parseTicket(fileText);

        TicketCalculator ticketCalculator = new TicketCalculator();

        String minFlightTimeByEveryCarrierText = ticketCalculator.getMinFlightTimeByEveryCarrier(tickets, originName, destinationName);

        String averagePriceAndMedianPriceByDirectionText
                = ticketCalculator.getAveragePriceAndMedianPriceByDirection(tickets, originName, destinationName);

        System.out.println(minFlightTimeByEveryCarrierText + "\n" + averagePriceAndMedianPriceByDirectionText);
    }
}