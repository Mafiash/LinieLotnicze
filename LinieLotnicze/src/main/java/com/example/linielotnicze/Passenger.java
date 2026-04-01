package com.example.linielotnicze;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    
    private String lastName;
    
    private String email;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reservation> reservations;
}
