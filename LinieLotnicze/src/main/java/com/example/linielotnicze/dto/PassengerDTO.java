package com.example.linielotnicze.dto;

import com.example.linielotnicze.Passenger;
import com.example.linielotnicze.controller.PassengerController;
import com.example.linielotnicze.controller.ReservationController;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PassengerDTO extends RepresentationModel<PassengerDTO> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public PassengerDTO(Passenger passenger) {
        this.id = passenger.getId();
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.email = passenger.getEmail();

        this.add(linkTo(methodOn(PassengerController.class).getPassenger(id)).withSelfRel());
        this.add(linkTo(methodOn(ReservationController.class).getReservations(null, null, email, null, null, null)).withRel("reservations"));
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}