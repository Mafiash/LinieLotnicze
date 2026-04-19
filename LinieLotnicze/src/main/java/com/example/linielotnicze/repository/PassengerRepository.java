package com.example.linielotnicze.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Passenger;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {
}
