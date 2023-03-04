package com.example.if_else.Reposiories;

import com.example.if_else.Models.Animal;
import com.example.if_else.enums.Gender;
import com.example.if_else.enums.LifeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query(nativeQuery = true,
            value = "SELECT *" +
                    "FROM  animals " +
                    "WHERE ( CAST( :startDateTime as timestamp ) is null or chipping_date_time >= :startDateTime) " +
                    "AND (CAST( :endDateTime as timestamp ) is null or chipping_date_time <= :endDateTime) " +
                    "AND (:chippingLocationId is null or chipping_location_id = :chippingLocationId) " +
                    "AND (:lifeStatus is null  or life_status = :lifeStatus) " +
                    "AND (:gender is null or gender = :gender) " +
                    "order by(id) limit :limit offset :offset")
    List<Animal> findAnimalByParams(@Param("startDateTime") Date startDateTime,
                                    @Param("endDateTime") Date endDateTime,
                                    @Param("chippingLocationId") Long chippingLocationId,
                                    @Param("lifeStatus") LifeStatus lifeStatus,
                                    @Param("gender") Gender gender,
                                    @Param("limit") int limit,
                                    @Param("offset") int offset);
}
