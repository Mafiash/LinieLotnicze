package com.example.linielotnicze.controller;

import com.example.linielotnicze.Reservation;
import com.example.linielotnicze.dto.ReservationDTO;
import com.example.linielotnicze.dto.PassengerDTO;
import com.example.linielotnicze.dto.FlightDTO;
import com.example.linielotnicze.exception.ResourceNotFoundException;
import com.example.linielotnicze.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> addReservation(@RequestBody Reservation reservation) {
        Reservation saved = reservationService.save(reservation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ReservationDTO(saved));
    }

    @GetMapping("/{id}")
    public ReservationDTO getReservation(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) {
            throw new ResourceNotFoundException("Brak rezerwacji o podanym ID");
        }
        return new ReservationDTO(r);
    }

    @GetMapping
    public Iterable<ReservationDTO> getReservations(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String sortBy
    ) {
        Iterable<Reservation> reservations;
        if (flightNumber != null) {
            if ("seatNumber".equalsIgnoreCase(sortBy)) {
                reservations = reservationService.findByFlightSortedBySeat(flightNumber);
            } else {
                reservations = reservationService.findByFlightNumber(flightNumber);
            }
        } else if (city != null) {
            reservations = reservationService.findByDestinationCity(city);
        } else if (email != null) {
            reservations = reservationService.findByPassengerEmail(email);
        } else if (maxPrice != null) {
            reservations = reservationService.findCheapReservations(maxPrice);
        } else if (lastName != null) {
            reservations = reservationService.findByPassengerLastName(lastName);
        } else {
            reservations = reservationService.findAll();
        }

        return StreamSupport.stream(reservations.spliterator(), false)
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        Reservation updated = reservationService.save(reservation);
        return ResponseEntity.ok(new ReservationDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long countByFlight(@RequestParam String flightNumber) {
        return reservationService.countReservationsForFlight(flightNumber);
    }

    @GetMapping("/{id}/passenger")
    public PassengerDTO getPassengerForReservation(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) {
            throw new ResourceNotFoundException("Brak rezerwacji o podanym ID");
        }
        if (r.getPassenger() == null) {
            throw new ResourceNotFoundException("Brak pasażera przypisanego do tej rezerwacji");
        }
        return new PassengerDTO(r.getPassenger());
    }

    @GetMapping("/{id}/flight")
    public FlightDTO getFlightForReservation(@PathVariable Long id) {
        Reservation r = reservationService.findById(id);
        if (r == null) {
            throw new ResourceNotFoundException("Brak rezerwacji o podanym ID");
        }
        if (r.getFlight() == null) {
            throw new ResourceNotFoundException("Brak lotu przypisanego do tej rezerwacji");
        }
        return new FlightDTO(r.getFlight());
    }
}
