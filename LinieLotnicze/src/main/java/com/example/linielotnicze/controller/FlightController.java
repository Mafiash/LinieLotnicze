package com.example.linielotnicze.controller;

import com.example.linielotnicze.Flight;
import com.example.linielotnicze.dto.FlightDTO;
import com.example.linielotnicze.dto.AirplaneDTO;
import com.example.linielotnicze.dto.AirportDTO;
import com.example.linielotnicze.exception.ResourceNotFoundException;
import com.example.linielotnicze.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightDTO> addFlight(@RequestBody Flight flight) {
        Flight saved = flightService.save(flight);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(new FlightDTO(saved));
    }

    @GetMapping("/{id}")
    public FlightDTO getFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) {
            throw new ResourceNotFoundException("Brak lotu o podanym ID");
        }
        return new FlightDTO(f);
    }

    @GetMapping
    public Iterable<FlightDTO> getFlights(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String sortBy
    ) {
        Iterable<Flight> flights;
        if (origin != null && destination != null) {
            flights = flightService.findRoute(origin, destination);
        } else if (origin != null) {
            flights = flightService.findByOriginCity(origin);
        } else if (destination != null) {
            if ("departureTime".equalsIgnoreCase(sortBy)) {
                flights = flightService.findFlightsToCitySorted(destination);
            } else {
                flights = flightService.findByDestinationCity(destination);
            }
        } else if (flightNumber != null) {
            Flight f = flightService.findByFlightNumber(flightNumber);
            flights = f != null ? List.of(f) : List.of();
        } else if ("departureTime".equalsIgnoreCase(sortBy)) {
            flights = flightService.findAllSortedByDeparture();
        } else {
            flights = flightService.findAll();
        }

        return StreamSupport.stream(flights.spliterator(), false)
                .map(FlightDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        flight.setId(id);
        Flight updated = flightService.save(flight);
        return ResponseEntity.ok(new FlightDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/airplane")
    public AirplaneDTO getAirplaneForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) {
            throw new ResourceNotFoundException("Brak lotu o podanym ID");
        }
        if (f.getAirplane() == null) {
            throw new ResourceNotFoundException("Brak samolotu przypisanego do tego lotu");
        }
        return new AirplaneDTO(f.getAirplane());
    }

    @GetMapping("/{id}/origin")
    public AirportDTO getOriginForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) {
            throw new ResourceNotFoundException("Brak lotu o podanym ID");
        }
        if (f.getOrigin() == null) {
            throw new ResourceNotFoundException("Brak lotniska odlotu dla tego lotu");
        }
        return new AirportDTO(f.getOrigin());
    }

    @GetMapping("/{id}/destination")
    public AirportDTO getDestinationForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) {
            throw new ResourceNotFoundException("Brak lotu o podanym ID");
        }
        if (f.getDestination() == null) {
            throw new ResourceNotFoundException("Brak lotniska przylotu dla tego lotu");
        }
        return new AirportDTO(f.getDestination());
    }
}
