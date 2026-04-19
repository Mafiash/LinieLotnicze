package com.example.linielotnicze.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Airport;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Long> {
}
