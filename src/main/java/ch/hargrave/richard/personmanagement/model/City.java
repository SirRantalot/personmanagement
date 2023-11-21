package ch.hargrave.richard.personmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class City {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String city;

    public City() {
        // Default constructor
    }

    public City(String city) {
        this.city = city;
    }
}
