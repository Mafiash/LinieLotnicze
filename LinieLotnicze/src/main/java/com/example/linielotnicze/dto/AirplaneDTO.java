package com.example.linielotnicze.dto;

import com.example.linielotnicze.Airplane;
import com.example.linielotnicze.controller.AirplaneController;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AirplaneDTO extends RepresentationModel<AirplaneDTO> {
    private Long id;
    private String model;
    private Integer capacity;

    public AirplaneDTO(Airplane airplane) {
        this.id = airplane.getId();
        this.model = airplane.getModel();
        this.capacity = airplane.getCapacity();

        this.add(linkTo(methodOn(AirplaneController.class).getAirplaneHateoas(id)).withSelfRel());
    }

    public Long getId() { return id; }
    public String getModel() { return model; }
    public Integer getCapacity() { return capacity; }
}