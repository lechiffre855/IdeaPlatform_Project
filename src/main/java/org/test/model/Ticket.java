package org.test.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {

        private String origin;
        private String originName;
        private String destination;
        private String destinationName;
        private Date departure;
        private Date arrival;
        private String carrier;
        private int stops;
        private double price;

        @JsonCreator
        public Ticket(@JsonProperty("origin") String origin,
                      @JsonProperty("origin_name") String originName,
                      @JsonProperty("destination") String destination,
                      @JsonProperty("destination_name") String destinationName,
                      @JsonProperty("departure_date") String departureDate,
                      @JsonProperty("departure_time") String departureTime,
                      @JsonProperty("arrival_date") String arrivalDate,
                      @JsonProperty("arrival_time") String arrivalTime,
                      @JsonProperty("carrier") String carrier,
                      @JsonProperty("stops") int stops,
                      @JsonProperty("price") double price) {
                this.origin = origin;
                this.originName = originName;
                this.destination = destination;
                this.destinationName = destinationName;
                this.departure = performDate(departureDate, departureTime);
                this.arrival = performDate(arrivalDate, arrivalTime);
                this.carrier = carrier;
                this.stops = stops;
                this.price = price;
        }

        private Date performDate(String date, String time) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
            try {
                return formatter.parse(date + " " + time);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public String getOrigin() {
                return origin;
        }

        public String getOriginName() {
                return originName;
        }

        public String getDestination() {
                return destination;
        }

        public String getDestinationName() {
                return destinationName;
        }

        public Date getDeparture() {
                return departure;
        }

        public Date getArrival() {
                return arrival;
        }

        public String getCarrier() {
                return carrier;
        }

        public int getStops() {
                return stops;
        }

        public double getPrice() {
                return price;
        }
}
