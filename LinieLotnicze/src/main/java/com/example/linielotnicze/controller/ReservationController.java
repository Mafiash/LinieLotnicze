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

import com.example.linielotnicze.Flight;
import com.example.linielotnicze.Passenger;
import com.example.linielotnicze.Reservation;
import com.example.linielotnicze.service.ReservationService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(reservation));
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        if (reservation == null) throw new NoSuchElementException("Brak rezerwacji o podanym ID");
        return reservation;
    }

    @GetMapping
    public Iterable<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        return reservationService.save(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Iterable<Reservation> searchReservations(
            @RequestParam(required = false) String flight,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Double maxPrice) {
        if (flight != null) {
            return reservationService.findByFlightSortedBySeat(flight);
        }
        if (destination != null) {
            return reservationService.findByDestinationCity(destination);
        }
        if (email != null) {
            return reservationService.findByPassengerEmail(email);
        }
        if (lastName != null) {
            return reservationService.findByPassengerLastName(lastName);
        }
        if (maxPrice != null) {
            return reservationService.findCheapReservations(maxPrice);
        }
        return reservationService.findAll();
    }

    @GetMapping("/count")
    public long countByFlight(@RequestParam String flight) {
        return reservationService.countReservationsForFlight(flight);
    }

    @GetMapping("/{id}/passenger")
    public Passenger getPassengerForReservation(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) throw new NoSuchElementException("Brak rezerwacji o podanym ID");
        return r.getPassenger();
    }

    @GetMapping("/{id}/flight")
    public Flight getFlightForReservation(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) throw new NoSuchElementException("Brak rezerwacji o podanym ID");
        return r.getFlight();
    }

    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.ReservationDTO getReservationHateoas(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) throw new NoSuchElementException("Brak rezerwacji o podanym ID");
        return new com.example.linielotnicze.dto.ReservationDTO(r);
    }
}
