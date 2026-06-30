package com.example.linielotnicze.service;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.linielotnicze.Flight;
import com.example.linielotnicze.repository.FlightRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {

    private final FlightRepository flightRepo;

    @Transactional
    public Flight save(Flight flight) {
        return flightRepo.save(flight);
    }

    public Flight findById(Long id) {
        return flightRepo.findById(id).orElse(null);
    }

    public Iterable<Flight> findAll() {
        return flightRepo.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        flightRepo.deleteById(id);
    }
    
    public List<Flight> findByDestinationCity(String city) {
        return flightRepo.findByDestination_City(city);
    }
    
    public List<Flight> findRoute(String origin, String destination) {
        return flightRepo.findByOrigin_CityAndDestination_City(origin, destination);
    }
    
    public List<Flight> findByOriginCity(String city) {
        return flightRepo.findByOrigin_City(city);
    }
    
    public Flight findByFlightNumber(String flightNumber) {
        return flightRepo.findByFlightNumber(flightNumber); 
    }

    public List<Flight> findAllSortedByDeparture() {
        return flightRepo.findAllByOrderByDepartureTimeAsc();
    }

    public List<Flight> findFlightsToCitySorted(String city) {
        return flightRepo.findByDestination_CityOrderByDepartureTimeAsc(city);
    }
}