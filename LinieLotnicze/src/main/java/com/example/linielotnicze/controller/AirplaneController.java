package com.example.linielotnicze.controller;

import com.example.linielotnicze.Airplane;
import com.example.linielotnicze.dto.AirplaneDTO;
import com.example.linielotnicze.exception.ResourceNotFoundException;
import com.example.linielotnicze.service.AirplaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/airplanes")
@RequiredArgsConstructor
public class AirplaneController {

    private final AirplaneService airplaneService;

    @PostMapping
    public ResponseEntity<AirplaneDTO> addAirplane(@RequestBody Airplane airplane) {
        Airplane saved = airplaneService.save(airplane);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(new AirplaneDTO(saved));
    }

    @GetMapping("/{id}")
    public AirplaneDTO getAirplane(@PathVariable Long id) {
        Airplane a = airplaneService.findById(id);
        if (a == null) {
            throw new ResourceNotFoundException("Brak samolotu o podanym ID");
        }
        return new AirplaneDTO(a);
    }

    @GetMapping
    public Iterable<AirplaneDTO> getAirplanes(
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) String modelFragment,
            @RequestParam(required = false) String sortBy
    ) {
        Iterable<Airplane> airplanes;
        if (minCapacity != null && maxCapacity != null) {
            airplanes = airplaneService.findByCapacityRange(minCapacity, maxCapacity);
        } else if (minCapacity != null) {
            airplanes = airplaneService.findLargePlanes(minCapacity);
        } else if (modelFragment != null) {
            airplanes = airplaneService.findByModelFragment(modelFragment);
        } else if ("capacity".equalsIgnoreCase(sortBy)) {
            airplanes = airplaneService.findAllSortedByCapacityDesc();
        } else {
            airplanes = airplaneService.findAll();
        }

        return StreamSupport.stream(airplanes.spliterator(), false)
                .map(AirplaneDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirplaneDTO> updateAirplane(@PathVariable Long id, @RequestBody Airplane airplane) {
        airplane.setId(id);
        Airplane updated = airplaneService.save(airplane);
        return ResponseEntity.ok(new AirplaneDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirplane(@PathVariable Long id) {
        airplaneService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
