package com.example.linielotnicze.service;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.linielotnicze.Airport;
import com.example.linielotnicze.repository.AirportRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirportService {

    private final AirportRepository airportRepo;

    @Transactional
    public Airport save(Airport airport) {
        return airportRepo.save(airport);
    }

    public Airport findById(Long id) {
        return airportRepo.findById(id).orElse(null);
    }

    public Iterable<Airport> findAll() {
        return airportRepo.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        airportRepo.deleteById(id);
    }
    
    public Airport findByCode(String code) {
        return airportRepo.findByCode(code);
    }

    public List<Airport> findByCity(String city) {
        return airportRepo.findByCity(city);
    }

    public List<Airport> findAllSortedByCity() {
        return airportRepo.findAllByOrderByCityAsc();
    }

    public List<Airport> findByNameFragment(String nameFragment) {
        return airportRepo.findByNameContainingIgnoreCase(nameFragment);
    }
}
