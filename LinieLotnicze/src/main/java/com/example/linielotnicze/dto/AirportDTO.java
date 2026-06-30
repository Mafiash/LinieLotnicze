package com.example.linielotnicze.dto;

import com.example.linielotnicze.Airport;
import com.example.linielotnicze.controller.AirportController;
import com.example.linielotnicze.controller.FlightController;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AirportDTO extends RepresentationModel<AirportDTO> {
    private Long id;
    private String name;
    private String code;
    private String city;

    public AirportDTO(Airport airport) {
        this.id = airport.getId();
        this.name = airport.getName();
        this.code = airport.getCode();
        this.city = airport.getCity();

        this.add(linkTo(methodOn(AirportController.class).getAirport(id)).withSelfRel());
        
        this.add(linkTo(methodOn(FlightController.class).getFlights(city, null, null, null)).withRel("departures"));
        this.add(linkTo(methodOn(FlightController.class).getFlights(null, city, null, null)).withRel("arrivals"));
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getCity() { return city; }
}