package com.example.linielotnicze.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.linielotnicze.Airplane;
import com.example.linielotnicze.repository.AirplaneRepository;


@Service
public class AirplaneService {

    @Autowired
    AirplaneRepository airplaneRepo;

    public Airplane save(Airplane airplane) {
        return airplaneRepo.save(airplane);
    }

    public Airplane findById(Long id) {
        return airplaneRepo.findById(id).orElse(null);
    }

    public Iterable<Airplane> findAll() {
        return airplaneRepo.findAll();
    }

    public void deleteById(Long id) {
        airplaneRepo.deleteById(id);
    }
}