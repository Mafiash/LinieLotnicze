package com.example.linielotnicze.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
