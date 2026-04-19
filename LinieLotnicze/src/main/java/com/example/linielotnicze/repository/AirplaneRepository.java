package com.example.linielotnicze.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.linielotnicze.Airplane;

@Repository
public interface AirplaneRepository extends CrudRepository<Airplane, Long> {
}