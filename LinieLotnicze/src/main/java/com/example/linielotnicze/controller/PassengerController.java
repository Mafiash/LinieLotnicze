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

import com.example.linielotnicze.Passenger;
import com.example.linielotnicze.service.PassengerService;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @PostMapping
    public Passenger addPassenger(@RequestBody Passenger passenger) {
        return passengerService.save(passenger);
    }

    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        return passengerService.findById(id);
    }

    @GetMapping
    public Iterable<Passenger> getAllPassengers() {
        return passengerService.findAll();
    }
    
    @GetMapping("/search")
    public Iterable<Passenger> findByLastName(@RequestParam String lastName) {
        return passengerService.findByLastName(lastName);
    }

    @PutMapping
    public Passenger updatePassenger(@RequestBody Passenger passenger) {
        return passengerService.save(passenger);
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deleteById(id);
    }
    
    @GetMapping("/search/name-or")
    public Iterable<Passenger> getPassengersByNameOr(@RequestParam String firstName, @RequestParam String lastName) {
        return passengerService.findByFirstOrLastName(firstName, lastName);
    }

    @GetMapping("/search/email")
    public Iterable<Passenger> getPassengersByEmailDomain(@RequestParam String emailFragment) {
        return passengerService.findByEmailFragment(emailFragment);
    }

    @GetMapping("/sorted")
    public Iterable<Passenger> getAllPassengersSorted() {
        return passengerService.findAllSortedByLastName();
    }
}