package ch.hargrave.richard.personmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class ContactDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String street;

    @Column
    private String streetNumber;

    @Column
    private String email;

    @Column
    private Integer postalNumber;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private Country country;

    // Constructor
    public ContactDetails(String street, String streetNumber, String email, Integer postalNumber, String phoneNumber,
            City city, Country country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.email = email;
        this.postalNumber = postalNumber;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.country = country;
    }
}
