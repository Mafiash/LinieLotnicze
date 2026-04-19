package com.example.linielotnicze.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
