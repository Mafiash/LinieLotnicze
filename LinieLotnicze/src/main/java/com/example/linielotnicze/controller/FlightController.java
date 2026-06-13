package com.example.linielotnicze.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.linielotnicze.Flight;
import com.example.linielotnicze.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

    @GetMapping("/{id}")
    public Flight getFlight(@PathVariable Long id) {
        return flightService.findById(id);
    }

    @GetMapping
    public Iterable<Flight> getAllFlights() {
        return flightService.findAll();
    }

    @PutMapping
    public Flight updateFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteById(id);
    }
    
    @GetMapping("/search/destination")
    public Iterable<Flight> getFlightsByDestination(@RequestParam String city) {
        return flightService.findByDestinationCity(city);
    }
    
    @GetMapping("/search/origin")
    public Iterable<Flight> getFlightsByOrigin(@RequestParam String city) {
        return flightService.findByOriginCity(city);
    }
    
    @GetMapping("/search/route")
    public Iterable<Flight> getRoute(@RequestParam String origin, @RequestParam String destination) {
        return flightService.findRoute(origin, destination);
    }
    
    @GetMapping("/search/number")
    public Flight getFlightByNumber(@RequestParam String flightNumber) {
        return flightService.findByFlightNumber(flightNumber);
    }

    @GetMapping("/sorted")
    public Iterable<Flight> getAllFlightsSorted() {
        return flightService.findAllSortedByDeparture();
    }

    @GetMapping("/search/destination/sorted")
    public Iterable<Flight> getFlightsToCitySorted(@RequestParam String city) {
        return flightService.findFlightsToCitySorted(city);
    }

    @GetMapping("/{id}/airplane")
    public Object getAirplaneForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new java.util.NoSuchElementException("Brak lotu o podanym ID");
        return f.getAirplane();
    }

    @GetMapping("/{id}/origin")
    public Object getOriginForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new java.util.NoSuchElementException("Brak lotu o podanym ID");
        return f.getOrigin();
    }

    @GetMapping("/{id}/destination")
    public Object getDestinationForFlight(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new java.util.NoSuchElementException("Brak lotu o podanym ID");
        return f.getDestination();
    }

    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.FlightDTO getFlightHateoas(@PathVariable Long id) {
        Flight f = flightService.findById(id);
        if (f == null) throw new java.util.NoSuchElementException("Brak lotu o podanym ID");
        return new com.example.linielotnicze.dto.FlightDTO(f);
    }
}
