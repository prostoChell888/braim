package com.example.if_else.Models;


import com.example.if_else.utils.IdInterface;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visits_Locations")
@Builder
public class VisitsLocation implements IdInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "location_id",
            referencedColumnName = "id")
    private Location locationPointId;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_time_of_visit_location_point")
    @NotNull
    @Builder.Default
    private Date dateTimeOfVisitLocationPoint = new Date();

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonIgnore
    private Animal animal;

}
