package com.example.linielotnicze.controller;

import com.example.linielotnicze.Airport;
import com.example.linielotnicze.dto.AirportDTO;
import com.example.linielotnicze.exception.ResourceNotFoundException;
import com.example.linielotnicze.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportDTO> addAirport(@RequestBody Airport airport) {
        Airport saved = airportService.save(airport);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(new AirportDTO(saved));
    }

    @GetMapping("/{id}")
    public AirportDTO getAirport(@PathVariable Long id) {
        Airport a = airportService.findById(id);
        if (a == null) {
            throw new ResourceNotFoundException("Brak lotniska o podanym ID");
        }
        return new AirportDTO(a);
    }

    @GetMapping
    public Iterable<AirportDTO> getAirports(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String nameFragment,
            @RequestParam(required = false) String sortBy
    ) {
        Iterable<Airport> airports;
        if (code != null) {
            Airport a = airportService.findByCode(code);
            airports = a != null ? java.util.List.of(a) : java.util.List.of();
        } else if (city != null) {
            airports = airportService.findByCity(city);
        } else if (nameFragment != null) {
            airports = airportService.findByNameFragment(nameFragment);
        } else if ("city".equalsIgnoreCase(sortBy)) {
            airports = airportService.findAllSortedByCity();
        } else {
            airports = airportService.findAll();
        }

        return StreamSupport.stream(airports.spliterator(), false)
                .map(AirportDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportDTO> updateAirport(@PathVariable Long id, @RequestBody Airport airport) {
        airport.setId(id);
        Airport updated = airportService.save(airport);
        return ResponseEntity.ok(new AirportDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
