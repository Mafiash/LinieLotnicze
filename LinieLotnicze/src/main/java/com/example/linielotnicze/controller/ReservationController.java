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

import com.example.linielotnicze.Reservation;
import com.example.linielotnicze.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @GetMapping
    public Iterable<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    @PutMapping
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
    
    @GetMapping("/search/flight")
    public Iterable<Reservation> getReservationsByFlight(@RequestParam String flightNumber) {
        return reservationService.findByFlightNumber(flightNumber);
    }

    @GetMapping("/search/destination")
    public Iterable<Reservation> getReservationsByDestinationCity(@RequestParam String city) {
        return reservationService.findByDestinationCity(city);
    }
    
    @GetMapping("/search/passenger")
    public Iterable<Reservation> getReservationsByEmail(@RequestParam String email) {
        return reservationService.findByPassengerEmail(email);
    }

    @GetMapping("/count")
    public long countByFlight(@RequestParam String flightNumber) {
        return reservationService.countReservationsForFlight(flightNumber);
    }
    
    @GetMapping("/search/price")
    public Iterable<Reservation> getCheapReservations(@RequestParam Double maxPrice) {
        return reservationService.findCheapReservations(maxPrice);
    }

    @GetMapping("/search/passenger/lastname")
    public Iterable<Reservation> getReservationsByLastName(@RequestParam String lastName) {
        return reservationService.findByPassengerLastName(lastName);
    }

    @GetMapping("/search/flight/sorted")
    public Iterable<Reservation> getReservationsByFlightSorted(@RequestParam String flightNumber) {
        return reservationService.findByFlightSortedBySeat(flightNumber);
    }
    
    @GetMapping("/{id}/passenger")
    public Object getPassengerForReservation(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) throw new java.util.NoSuchElementException("Brak rezerwacji");
        return r.getPassenger();
    }

    @GetMapping("/{id}/flight")
    public Object getFlightForReservation(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) throw new java.util.NoSuchElementException("Brak rezerwacji");
        return r.getFlight();
    }

    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.ReservationDTO getReservationHateoas(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) throw new java.util.NoSuchElementException("Brak rezerwacji");
        return new com.example.linielotnicze.dto.ReservationDTO(r);
    }
}
