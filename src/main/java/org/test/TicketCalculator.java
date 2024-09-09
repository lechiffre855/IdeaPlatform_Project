package org.test;

import org.test.model.Ticket;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class TicketCalculator {

    public String getMinFlightTimeByEveryCarrier(Ticket[] tickets, String originName, String destinationName){
        Map<String, Map<String, Set<Duration>>> flights = getMap_Direction_Carrier_Durations(tickets);
        String directionName = originName + " - " + destinationName;

        StringBuilder stringBuilder = new StringBuilder();

        Map<String, Set<Duration>> carrier_time_Map = flights.get(directionName);

        if (carrier_time_Map == null) {
            return "Билетов по данным направлениям нет в списке.";
        }

        stringBuilder.append("Минимальное время полета по направлению ").append(destinationName).append(":\n").append("\n");

        for (Map.Entry<String, Set<Duration>> entry : carrier_time_Map.entrySet()) {
            TreeSet<Duration> durationSet = (TreeSet<Duration>) entry.getValue();
            stringBuilder.append(entry.getKey()).append(" - ").append(durationSet.first()).append(";\n");
        }
        return stringBuilder.toString();
    }

    public String getAveragePriceAndMedianPriceByDirection(Ticket[] tickets, String originName, String destinationName) {
        String directionName = originName + " - " + destinationName;

        double averagePrice = getAveragePriceByDirection(tickets, originName, destinationName);
        double medianPrice = getMedianPriceByDirection(tickets, originName, destinationName);

        if (averagePrice == 0 || medianPrice == 0) {
            return "По данным направлениям нет билетов.";
        }
        int difference = (int) Math.abs(averagePrice - medianPrice);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("По направлению ").append(directionName).append(" ");
        if (averagePrice==medianPrice)
            stringBuilder.append("средняя цена и медиана равны.\n");
        else if (averagePrice>medianPrice)
            stringBuilder.append("средняя цена выше медианы.\n");
        else if (averagePrice<medianPrice)
            stringBuilder.append("медиана выше средней цены.\n");
        stringBuilder.append("Их разница равна ").append(difference).append(".");

        return stringBuilder.toString();
    }

    private List<Double> getPricesListByDirection(Ticket[] tickets, String originName, String destinationName) {
        List<Double> prices_List = new ArrayList<>();
        String directionName = originName + " - " + destinationName;

        for (Ticket ticket : tickets) {
            if ((ticket.getOriginName() + " - " + ticket.getDestination()).equals(directionName)) {
                prices_List.add(ticket.getPrice());
            }
        }
        return prices_List;
    }

    private double getAveragePriceByDirection(Ticket[] tickets, String originName, String destinationName) {
        List<Double> pricesList = getPricesListByDirection(tickets, originName, destinationName);

        double sum = 0;
        for (Double price : pricesList) {
            sum += price;
        }
        return sum / pricesList.size();
    }

    private double getMedianPriceByDirection(Ticket[] tickets, String originName, String destinationName) {

        List<Double> pricesList = getPricesListByDirection(tickets, originName, destinationName)
                .stream()
                .sorted()
                .toList();

        int listSize = pricesList.size();
        int modulo = listSize%2;

        if (modulo!=0){
            return pricesList.get(listSize/2);
        } else {
            double price1 = pricesList.get((listSize/2)-1);
            double price2 = pricesList.get(listSize/2);

            return (price1 + price2)/2;
        }
    }

    private Map<String, Map<String, Set<Duration>>> getMap_Direction_Carrier_Durations(Ticket[] tickets) {
        Map<String, Map<String, Set<Duration>>> flights = new HashMap<>();

        for (Ticket ticket : tickets) {
            String directionName = ticket.getOriginName() + " - " + ticket.getDestinationName();
            Duration duration = Duration.between(ticket.getDeparture().toInstant(), ticket.getArrival().toInstant());

            Map<String, Set<Duration>> carrier_time_Map = flights.get(directionName);

            if (carrier_time_Map == null) {
                carrier_time_Map = new HashMap<>();
                flights.put(directionName, carrier_time_Map);
            }

            Set<Duration> duration_Set = carrier_time_Map.get(ticket.getCarrier());

            if (duration_Set == null) {
                duration_Set = new TreeSet<>();
            }

            duration_Set.add(duration);

            carrier_time_Map.put(ticket.getCarrier(), duration_Set);

        }
        return flights;
    }

}
