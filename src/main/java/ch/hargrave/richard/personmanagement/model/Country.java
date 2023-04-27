package ch.hargrave.richard.personmanagement.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String nationality;

}
