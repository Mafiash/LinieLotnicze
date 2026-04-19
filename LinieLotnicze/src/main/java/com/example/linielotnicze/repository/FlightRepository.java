package com.example.linielotnicze.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {

}
