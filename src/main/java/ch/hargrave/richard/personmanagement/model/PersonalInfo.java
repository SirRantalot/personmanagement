package ch.hargrave.richard.personmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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

    private enum title{Mr, Mrs, Ms, divers};

    @OneToOne(optional = false)
    @JoinColumn(name = "contactDetails_id")
    private ContactDetails cotactDetails;

    @ManyToOne(optional = false)
    @JoinColumn(name = "nationality_id")
    private Country nationality;
}
