package com.example.if_else.Models;

import com.example.if_else.enums.Gender;
import com.example.if_else.enums.LifeStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "animal_type_id",
            referencedColumnName = "id" )

    private List<AnimalType> animalTypes;

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
    @Builder.Default
    private LifeStatus lifeStatus = LifeStatus.ALIVE;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "chipping_date_time")
    @NotNull
    @Builder.Default
    private Date chippingDateTime = new Date();


    @ManyToOne
    @JoinColumn(name = "chipper_id",
            referencedColumnName = "id")
    @NotNull
    private  Account chipperId;


    @OneToOne
    @JoinColumn(name = "chipping_location_id",
            referencedColumnName = "id")
    @NotNull
    private Location chippingLocationId;


    @OneToMany
    @JoinColumn(name = "visited_location_id",
            referencedColumnName = "id")
    @NotNull
    @Builder.Default
    private List<VisitsLocation> visitedLocations =new ArrayList<>();


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "death_date_time")
    private Date deathDateTime;



}
