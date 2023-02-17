package com.example.if_else.Models;

import lombok.*;

import javax.persistence.*;
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

    //Рзаобратся со связью
    @ManyToOne
    @JoinColumn(name = "animal_type_id")
    private AnimalType animalType;

    private Float weight;

    private Float length;

    private Float height;

    private String gender;

    private String lifeStatus;

   // private chippingDateTime;

    @ManyToOne
    @JoinColumn(name = "chipper_id_id")
    private  Account chipperId;

    @ManyToOne
    @JoinColumn(name = "chipping_location_id_id")
    private Location chippingLocationId;

    //Доделать!!!!!!!!!
    @ManyToMany
    private List<Location> visitedLocations;



  //  private deathDateTime;






}
