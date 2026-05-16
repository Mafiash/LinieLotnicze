package com.example.linielotnicze.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {
    List<Flight> findByDestination_City(String city);
    List<Flight> findByOrigin_CityAndDestination_City(String originCity, String destinationCity);
    List<Flight> findByOrigin_City(String city);
    Flight findByFlightNumber(String flightNumber);
    List<Flight> findAllByOrderByDepartureTimeAsc();
    List<Flight> findByDestination_CityOrderByDepartureTimeAsc(String city);
}
