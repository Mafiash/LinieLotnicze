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
import com.example.linielotnicze.service.AirplaneService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    @Autowired
    AirplaneService airplaneService;

    @PostMapping
    public ResponseEntity<Airplane> addAirplane(@RequestBody Airplane airplane) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airplaneService.save(airplane));
    }

    @GetMapping("/{id}")
    public Airplane getAirplane(@PathVariable Long id) {
        Airplane airplane = airplaneService.findById(id);
        if (airplane == null) throw new NoSuchElementException("Brak samolotu o podanym ID");
        return airplane;
    }

    @GetMapping
    public Iterable<Airplane> getAllAirplanes() {
        return airplaneService.findAll();
    }

    @PutMapping("/{id}")
    public Airplane updateAirplane(@PathVariable Long id, @RequestBody Airplane airplane) {
        airplane.setId(id);
        return airplaneService.save(airplane);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirplane(@PathVariable Long id) {
        airplaneService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Iterable<Airplane> searchAirplanes(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity) {
        if (model != null) {
            return airplaneService.findByModelFragment(model);
        }
        if (minCapacity != null && maxCapacity != null) {
            return airplaneService.findByCapacityRange(minCapacity, maxCapacity);
        }
        if (minCapacity != null) {
            return airplaneService.findLargePlanes(minCapacity);
        }
        return airplaneService.findAllSortedByCapacityDesc();
    }

    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.AirplaneDTO getAirplaneHateoas(@PathVariable Long id) {
        Airplane a = airplaneService.findById(id);
        if (a == null) throw new NoSuchElementException("Brak samolotu o podanym ID");
        return new com.example.linielotnicze.dto.AirplaneDTO(a);
    }
}
