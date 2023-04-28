package ch.hargrave.richard.personmanagement.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String nationality;

    public Country(String country, String nationality) {
        this.country = country;
        this.nationality = nationality;
    }
    public Country(String country, String nationality, long id) {
        this.country = country;
        this.nationality = nationality;
        this.id = id;
    }
}
