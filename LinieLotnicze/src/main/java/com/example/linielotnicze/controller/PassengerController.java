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

import com.example.linielotnicze.Passenger;
import com.example.linielotnicze.service.PassengerService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Passenger> addPassenger(@RequestBody Passenger passenger) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.save(passenger));
    }

    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        Passenger passenger = passengerService.findById(id);
        if (passenger == null) throw new NoSuchElementException("Brak pasazera o podanym ID");
        return passenger;
    }

    @GetMapping
    public Iterable<Passenger> getAllPassengers() {
        return passengerService.findAll();
    }

    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        passenger.setId(id);
        return passengerService.save(passenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Iterable<Passenger> searchPassengers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {
        if (email != null) {
            return passengerService.findByEmailFragment(email);
        }
        if (firstName != null && lastName != null) {
            return passengerService.findByFirstOrLastName(firstName, lastName);
        }
        if (lastName != null) {
            return passengerService.findByLastName(lastName);
        }
        return passengerService.findAllSortedByLastName();
    }

    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.PassengerDTO getPassengerHateoas(@PathVariable Long id) {
        Passenger p = passengerService.findById(id);
        if (p == null) throw new NoSuchElementException("Brak pasazera o podanym ID");
        return new com.example.linielotnicze.dto.PassengerDTO(p);
    }
}
