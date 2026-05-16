package com.example.linielotnicze.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.linielotnicze.Passenger;
import java.util.List;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {
    List<Passenger> findByLastName(String lastName);
    List<Passenger> findByFirstNameOrLastName(String firstName, String lastName);
    List<Passenger> findByEmailContainingIgnoreCase(String emailFragment);
    List<Passenger> findAllByOrderByLastNameAsc();
}
