package com.example.linielotnicze.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linielotnicze.Passenger;
import com.example.linielotnicze.repository.PassengerRepository;

@Service
public class PassengerService {

    @Autowired
    PassengerRepository passengerRepo;

    public Passenger save(Passenger passenger) {
        return passengerRepo.save(passenger);
    }

    public Passenger findById(Long id) {
        return passengerRepo.findById(id).orElse(null);
    }

    public Iterable<Passenger> findAll() {
        return passengerRepo.findAll();
    }

    public void deleteById(Long id) {
        passengerRepo.deleteById(id);
    }
    
    public List<Passenger> findByLastName(String lastName) {
        return passengerRepo.findByLastName(lastName);
    }
    
    public List<Passenger> findByFirstOrLastName(String firstName, String lastName) {
        return passengerRepo.findByFirstNameOrLastName(firstName, lastName);
    }

    public List<Passenger> findByEmailFragment(String emailFragment) {
        return passengerRepo.findByEmailContainingIgnoreCase(emailFragment);
    }

    public List<Passenger> findAllSortedByLastName() {
        return passengerRepo.findAllByOrderByLastNameAsc();
    }
}