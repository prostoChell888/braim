package com.example.if_else.Models;


import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "latitude")
    @Range(min = -90, max = 90)
    @NotNull
    private Double latitude;

    @Column(name = "longitude")
    @Range(min = -180, max = 180)
    @NotNull
    private Double longitude;

}
