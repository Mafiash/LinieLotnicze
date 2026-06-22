package com.example.linielotnicze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.linielotnicze.Airplane;
import com.example.linielotnicze.Airport;
import com.example.linielotnicze.Flight;
import com.example.linielotnicze.service.FlightService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.save(flight));
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        Flight flight = flightService.findById(id);
        if (flight == null) throw new NoSuchElementException("Brak lotu o podanym ID");
        return flight;
    }

    @GetMapping
    public Iterable<Flight> getAllFlights() {
        return flightService.findAll();
    }

    @PutMapping("/{id}")
    public Flight updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        flight.setId(id);
        return flightService.save(flight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Iterable<Flight> searchFlights(
            @RequestParam(required = false) String number,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {
        if (number != null) {
            Flight flight = flightService.findByFlightNumber(number);
            return flight != null ? List.of(flight) : List.of();
        }
        if (origin != null && destination != null) {
            return flightService.findRoute(origin, destination);
        }
        if (origin != null) {
            return flightService.findByOriginCity(origin);
        }
        if (destination != null) {
            return flightService.findFlightsToCitySorted(destination);
        }
        return flightService.findAllSortedByDeparture();
    }

    @GetMapping("/{id}/airplane")
    public Airplane getAirplaneForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new NoSuchElementException("Brak lotu o podanym ID");
        return f.getAirplane();
    }

    @GetMapping("/{id}/origin")
    public Airport getOriginForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new NoSuchElementException("Brak lotu o podanym ID");
        return f.getOrigin();
    }

    @GetMapping("/{id}/destination")
    public Airport getDestinationForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new NoSuchElementException("Brak lotu o podanym ID");
        return f.getDestination();
    }

    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.FlightDTO getFlightHateoas(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new NoSuchElementException("Brak lotu o podanym ID");
        return new com.example.linielotnicze.dto.FlightDTO(f);
    }
}
