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

import com.example.linielotnicze.Airport;
import com.example.linielotnicze.service.AirportService;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    AirportService airportService;

    @PostMapping
    public Airport addAirport(@RequestBody Airport airport) {
        return airportService.save(airport);
    }

    @GetMapping("/{id}")
    public Airport getAirport(@PathVariable Long id) {
        return airportService.findById(id);
    }

    @GetMapping
    public Iterable<Airport> getAllAirports() {
        return airportService.findAll();
    }

    @PutMapping
    public Airport updateAirport(@RequestBody Airport airport) {
        return airportService.save(airport);
    }

    @DeleteMapping("/{id}")
    public void deleteAirport(@PathVariable Long id) {
        airportService.deleteById(id);
    }
    
    @GetMapping("/search/code")
    public Airport getAirportByCode(@RequestParam String code) {
        return airportService.findByCode(code);
    }

    @GetMapping("/search/city")
    public Iterable<Airport> getAirportsByCity(@RequestParam String city) {
        return airportService.findByCity(city);
    }

    @GetMapping("/sorted-by-city")
    public Iterable<Airport> getAllAirportsSorted() {
        return airportService.findAllSortedByCity();
    }

    @GetMapping("/search/name")
    public Iterable<Airport> getAirportsByName(@RequestParam String nameFragment) {
        return airportService.findByNameFragment(nameFragment);
    }
}
