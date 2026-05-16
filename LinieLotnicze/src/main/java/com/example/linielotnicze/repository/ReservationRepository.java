package com.example.linielotnicze.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByFlight_FlightNumber(String flightNumber);
    
    List<Reservation> findByFlight_Destination_City(String city);
 
    List<Reservation> findByPassenger_Email(String email);
    
    long countByFlight_FlightNumber(String flightNumber);

    List<Reservation> findByPriceLessThanEqual(Double maxPrice);

    List<Reservation> findByPassenger_LastNameIgnoreCase(String lastName);

    List<Reservation> findByFlight_FlightNumberOrderBySeatNumberAsc(String flightNumber);
}
