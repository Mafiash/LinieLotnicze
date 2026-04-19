package com.example.linielotnicze.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linielotnicze.Reservation;
import com.example.linielotnicze.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepo;

    public Reservation save(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepo.findById(id).orElse(null);
    }

    public Iterable<Reservation> findAll() {
        return reservationRepo.findAll();
    }

    public void deleteById(Long id) {
        reservationRepo.deleteById(id);
    }
}
