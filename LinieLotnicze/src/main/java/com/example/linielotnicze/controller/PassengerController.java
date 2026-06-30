package com.example.linielotnicze.controller;

import com.example.linielotnicze.Passenger;
import com.example.linielotnicze.dto.PassengerDTO;
import com.example.linielotnicze.exception.ResourceNotFoundException;
import com.example.linielotnicze.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerDTO> addPassenger(@RequestBody Passenger passenger) {
        Passenger saved = passengerService.save(passenger);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(new PassengerDTO(saved));
    }

    @GetMapping("/{id}")
    public PassengerDTO getPassenger(@PathVariable Long id) {
        Passenger p = passengerService.findById(id);
        if (p == null) {
            throw new ResourceNotFoundException("Brak pasazera o podanym ID");
        }
        return new PassengerDTO(p);
    }

    @GetMapping
    public Iterable<PassengerDTO> getPassengers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String emailFragment,
            @RequestParam(required = false) String sortBy
    ) {
        Iterable<Passenger> passengers;
        if (firstName != null && lastName != null) {
            passengers = passengerService.findByFirstOrLastName(firstName, lastName);
        } else if (lastName != null) {
            passengers = passengerService.findByLastName(lastName);
        } else if (emailFragment != null) {
            passengers = passengerService.findByEmailFragment(emailFragment);
        } else if ("lastName".equalsIgnoreCase(sortBy)) {
            passengers = passengerService.findAllSortedByLastName();
        } else {
            passengers = passengerService.findAll();
        }

        return StreamSupport.stream(passengers.spliterator(), false)
                .map(PassengerDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        passenger.setId(id);
        Passenger updated = passengerService.save(passenger);
        return ResponseEntity.ok(new PassengerDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}