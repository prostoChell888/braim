package com.example.if_else.Models;

import com.example.if_else.enums.Gender;
import com.example.if_else.enums.LifeStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "animal_type_id",
            referencedColumnName = "id")
    private AnimalType animalType;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "length")
    private Float length;

    @Column(name = "height")
    private Float height;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "life_status")
    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "chipping_date_time")
    @NotNull
    private Date chippingDateTime;


    @ManyToOne
    @JoinColumn(name = "chipper_id",
            referencedColumnName = "id")
    private  Account chipperId;


    @OneToOne
    @JoinColumn(name = "chipping_location_id",
            referencedColumnName = "id")
    private Location chippingLocationId;


    @OneToMany
    @JoinColumn(name = "visited_location_id",
            referencedColumnName = "id")
    private List<VisitsLocation> visitedLocations;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "death_date_time")
    @NotNull
    private Date deathDateTime;


}
