package com.example.linielotnicze.dto;

import com.example.linielotnicze.Reservation;
import com.example.linielotnicze.Passenger;
import com.example.linielotnicze.Flight;
import com.example.linielotnicze.controller.ReservationController;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ReservationDTO extends RepresentationModel<ReservationDTO> {
    private Long id;
    private String seatNumber;
    private Double price;
    private Passenger passenger;
    private Flight flight;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.seatNumber = reservation.getSeatNumber();
        this.price = reservation.getPrice();
        this.passenger = reservation.getPassenger();
        this.flight = reservation.getFlight();

        this.add(linkTo(methodOn(ReservationController.class).getPassengerForReservation(id)).withRel("passenger"));
        this.add(linkTo(methodOn(ReservationController.class).getFlightForReservation(id)).withRel("flight"));
        this.add(linkTo(methodOn(ReservationController.class).getReservation(id)).withSelfRel());
    }

    public Long getId() { return id; }
    public String getSeatNumber() { return seatNumber; }
    public Double getPrice() { return price; }
    public Passenger getPassenger() { return passenger; }
    public Flight getFlight() { return flight; }
}
