package com.example.linielotnicze.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linielotnicze.Flight;
import com.example.linielotnicze.repository.FlightRepository;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepo;

    public Flight save(Flight flight) {
        return flightRepo.save(flight);
    }

    public Flight findById(Long id) {
        return flightRepo.findById(id).orElse(null);
    }

    public Iterable<Flight> findAll() {
        return flightRepo.findAll();
    }

    public void deleteById(Long id) {
        flightRepo.deleteById(id);
    }
}