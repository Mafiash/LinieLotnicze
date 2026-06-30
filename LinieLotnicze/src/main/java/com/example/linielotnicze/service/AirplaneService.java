package com.example.linielotnicze.service;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.linielotnicze.Airplane;
import com.example.linielotnicze.repository.AirplaneRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirplaneService {

    private final AirplaneRepository airplaneRepo;

    @Transactional
    public Airplane save(Airplane airplane) {
        return airplaneRepo.save(airplane);
    }

    public Airplane findById(Long id) {
        return airplaneRepo.findById(id).orElse(null);
    }

    public Iterable<Airplane> findAll() {
        return airplaneRepo.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        airplaneRepo.deleteById(id);
    }
    
    public List<Airplane> findLargePlanes(Integer capacity) {
        return airplaneRepo.findByCapacityGreaterThanEqual(capacity);
    }
    
    public List<Airplane> findByModelFragment(String modelFragment) {
        return airplaneRepo.findByModelContainingIgnoreCase(modelFragment);
    }

    public List<Airplane> findAllSortedByCapacityDesc() {
        return airplaneRepo.findAllByOrderByCapacityDesc();
    }

    public List<Airplane> findByCapacityRange(Integer min, Integer max) {
        return airplaneRepo.findByCapacityBetween(min, max);
    }
}