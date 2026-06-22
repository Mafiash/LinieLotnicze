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

import com.example.linielotnicze.Airport;
import com.example.linielotnicze.service.AirportService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    AirportService airportService;

    @PostMapping
    public ResponseEntity<Airport> addAirport(@RequestBody Airport airport) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airportService.save(airport));
    }

    @GetMapping("/{id}")
    public Airport getAirport(@PathVariable Long id) {
        Airport airport = airportService.findById(id);
        if (airport == null) throw new NoSuchElementException("Brak lotniska o podanym ID");
        return airport;
    }

    @GetMapping
    public Iterable<Airport> getAllAirports() {
        return airportService.findAll();
    }

    @PutMapping("/{id}")
    public Airport updateAirport(@PathVariable Long id, @RequestBody Airport airport) {
        airport.setId(id);
        return airportService.save(airport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Iterable<Airport> searchAirports(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String city) {
        if (code != null) {
            Airport airport = airportService.findByCode(code);
            return airport != null ? List.of(airport) : List.of();
        }
        if (city != null) {
            return airportService.findByCity(city);
        }
        if (name != null) {
            return airportService.findByNameFragment(name);
        }
        return airportService.findAllSortedByCity();
    }

    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.AirportDTO getAirportHateoas(@PathVariable Long id) {
        Airport a = airportService.findById(id);
        if (a == null) throw new NoSuchElementException("Brak lotniska o podanym ID");
        return new com.example.linielotnicze.dto.AirportDTO(a);
    }
}
