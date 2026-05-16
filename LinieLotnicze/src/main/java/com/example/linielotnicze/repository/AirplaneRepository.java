package com.example.linielotnicze.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Airplane;

@Repository
public interface AirplaneRepository extends CrudRepository<Airplane, Long> {
	List<Airplane> findByCapacityGreaterThanEqual(Integer capacity);
	List<Airplane> findByModelContainingIgnoreCase(String modelFragment);
    List<Airplane> findAllByOrderByCapacityDesc();
    List<Airplane> findByCapacityBetween(Integer minCapacity, Integer maxCapacity);
}