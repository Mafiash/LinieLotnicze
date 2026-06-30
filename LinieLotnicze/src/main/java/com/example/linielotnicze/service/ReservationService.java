package com.example.linielotnicze.service;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.linielotnicze.Reservation;
import com.example.linielotnicze.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepo;

    @Transactional
    public Reservation save(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepo.findById(id).orElse(null);
    }

    public Iterable<Reservation> findAll() {
        return reservationRepo.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        reservationRepo.deleteById(id);
    }
    
    public List<Reservation> findByFlightNumber(String flightNumber) {
        return reservationRepo.findByFlight_FlightNumber(flightNumber);
    }

    public List<Reservation> findByDestinationCity(String city) {
        return reservationRepo.findByFlight_Destination_City(city);
    }
    
    public List<Reservation> findByPassengerEmail(String email) {
        return reservationRepo.findByPassenger_Email(email);
    }

    public long countReservationsForFlight(String flightNumber) {
        return reservationRepo.countByFlight_FlightNumber(flightNumber);
    }
    
    public List<Reservation> findCheapReservations(Double maxPrice) {
        return reservationRepo.findByPriceLessThanEqual(maxPrice);
    }

    public List<Reservation> findByPassengerLastName(String lastName) {
        return reservationRepo.findByPassenger_LastNameIgnoreCase(lastName);
    }

    public List<Reservation> findByFlightSortedBySeat(String flightNumber) {
        return reservationRepo.findByFlight_FlightNumberOrderBySeatNumberAsc(flightNumber);
    }
}
