package com.example.linielotnicze.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Airport;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Long> {
	Airport findByCode(String code);
    List<Airport> findByCity(String city);
    List<Airport> findAllByOrderByCityAsc();
    List<Airport> findByNameContainingIgnoreCase(String nameFragment);
}
