package com.example.linielotnicze.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linielotnicze.Airport;
import com.example.linielotnicze.repository.AirportRepository;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepo;

    public Airport save(Airport airport) {
        return airportRepo.save(airport);
    }

    public Airport findById(Long id) {
        return airportRepo.findById(id).orElse(null);
    }

    public Iterable<Airport> findAll() {
        return airportRepo.findAll();
    }

    public void deleteById(Long id) {
        airportRepo.deleteById(id);
    }
}
