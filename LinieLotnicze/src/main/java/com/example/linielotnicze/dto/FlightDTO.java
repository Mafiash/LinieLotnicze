package com.example.linielotnicze.dto;

import com.example.linielotnicze.Flight;
import com.example.linielotnicze.Airport;
import com.example.linielotnicze.Airplane;
import com.example.linielotnicze.controller.FlightController;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class FlightDTO extends RepresentationModel<FlightDTO> {
    
    private Long id;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Airport origin;
    private Airport destination;
    private Airplane airplane;

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.flightNumber = flight.getFlightNumber();
        this.departureTime = flight.getDepartureTime();
        this.arrivalTime = flight.getArrivalTime();
        this.origin = flight.getOrigin();
        this.destination = flight.getDestination();
        this.airplane = flight.getAirplane();

        this.add(linkTo(methodOn(FlightController.class).getAirplaneForFlight(id)).withRel("airplane"));
        this.add(linkTo(methodOn(FlightController.class).getOriginForFlight(id)).withRel("origin"));
        this.add(linkTo(methodOn(FlightController.class).getDestinationForFlight(id)).withRel("destination"));
        
        this.add(linkTo(methodOn(FlightController.class).getFlight(id)).withSelfRel());
    }

    public Long getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public Airport getOrigin() { return origin; }
    public Airport getDestination() { return destination; }
    public Airplane getAirplane() { return airplane; }
}