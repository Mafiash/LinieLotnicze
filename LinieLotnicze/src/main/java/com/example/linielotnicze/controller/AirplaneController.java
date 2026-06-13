package com.example.linielotnicze.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.linielotnicze.Airplane;
import com.example.linielotnicze.service.AirplaneService;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    @Autowired
    AirplaneService airplaneService;

    @PostMapping
    public Airplane addAirplane(@RequestBody Airplane airplane) {
        return airplaneService.save(airplane);
    }

    @GetMapping("/{id}")
    public Airplane getAirplane(@PathVariable Long id) {
        return airplaneService.findById(id);
    }

    @GetMapping
    public Iterable<Airplane> getAllAirplanes() {
        return airplaneService.findAll();
    }

    @PutMapping
    public Airplane updateAirplane(@RequestBody Airplane airplane) {
        return airplaneService.save(airplane);
    }

    @DeleteMapping("/{id}")
    public void deleteAirplane(@PathVariable Long id) {
        airplaneService.deleteById(id);
    }
    
    @GetMapping("/search/capacity")
    public Iterable<Airplane> getLargePlanes(@RequestParam Integer minCapacity) {
        return airplaneService.findLargePlanes(minCapacity);
    }
    
    @GetMapping("/search/model")
    public Iterable<Airplane> getPlanesByModel(@RequestParam String modelFragment) {
        return airplaneService.findByModelFragment(modelFragment);
    }

    @GetMapping("/sorted-by-capacity")
    public Iterable<Airplane> getPlanesSortedByCapacity() {
        return airplaneService.findAllSortedByCapacityDesc();
    }

    @GetMapping("/search/capacity-range")
    public Iterable<Airplane> getPlanesByCapacityRange(@RequestParam Integer min, @RequestParam Integer max) {
        return airplaneService.findByCapacityRange(min, max);
    }
    
    @GetMapping("/{id}/hateoas")
    public com.example.linielotnicze.dto.AirplaneDTO getAirplaneHateoas(@PathVariable Long id) {
        Airplane a = airplaneService.findById(id);
        if (a == null) throw new java.util.NoSuchElementException("Brak samolotu o podanym ID");
        return new com.example.linielotnicze.dto.AirplaneDTO(a);
    }
}
