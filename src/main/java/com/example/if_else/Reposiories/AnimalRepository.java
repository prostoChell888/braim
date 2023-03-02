package com.example.if_else.Reposiories;

import com.example.if_else.Models.Animal;
import com.example.if_else.utils.SerchingParametrs.AmimalSerchParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT animals" +
            " FROM Animal animals " +
            "WHERE ( cast( :startDateTime as timestamp ) is null or animals.chippingDateTime >= :startDateTime) " +
            "AND (cast( :endDateTime as timestamp ) is null or animals.chippingDateTime <= :endDateTime) " +
            "AND (:chippingLocationId is null or animals.chippingLocationId.id = :chippingLocationId) " +
            "AND (:lifeStatus is null  or animals.lifeStatus = :lifeStatus) " +
            "AND (:gender is null or animals.gender = :gender)")
    List<Animal> findAnimalByParams(@Param("startDateTime")Date startDateTime,
                                    @Param("endDateTime")Date endDateTime,
                                    @Param("chippingLocationId")Long chippingLocationId,
                                    @Param("lifeStatus")String lifeStatus,
                                    @Param("gender")String gender);
}
