package ch.hargrave.richard.personmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PersonalInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private Integer age;

    @OneToOne(optional = false)
    @JoinColumn(name = "contactDetails_id")
    private ContactDetails contactDetails;

    @ManyToOne(optional = false)
    @JoinColumn(name = "nationality_id")
    private Country nationality;
}
