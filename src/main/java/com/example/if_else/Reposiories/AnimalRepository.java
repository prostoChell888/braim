package com.example.if_else.Reposiories;

import com.example.if_else.Models.Account;
import com.example.if_else.Models.Animal;
import com.example.if_else.Models.Location;
import com.example.if_else.enums.Gender;
import com.example.if_else.enums.LifeStatus;
import com.example.if_else.mapers.AnimalProjection;
import com.example.if_else.mapers.VisitsLocationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long>,
        JpaSpecificationExecutor<AnimalProjection> {
    List<Animal> findByChipperId(Account chipperId);

    @Query(
            "SELECT animals " +
                    "FROM  Animal animals " +
                    "WHERE(CAST(:startDateTime  as timestamp ) is null or animals.chippingDateTime >= :startDateTime) " +
                    "AND (CAST(:endDateTime  as timestamp ) is null or animals.chippingDateTime <= :endDateTime) " +
                    "AND (:chippingLocationId  is null or :chippingLocationId  = animals.chippingLocationId.id) " +
                    "AND (:lifeStatus is null or  animals.lifeStatus = :lifeStatus ) " +
                    "AND (:gender  is null or animals.gender = :gender ) ")
    Page<Animal> findAnimalByParams(
            @Param("startDateTime") @Temporal Date startDateTime,
            @Param("endDateTime") @Temporal Date endDateTime,
            @Param("chippingLocationId") Long chippingLocationId,
            @Param("lifeStatus") LifeStatus lifeStatus,
            @Param("gender") Gender gender,
            Pageable pageable);

    @Query("SELECT an " +
            "FROM Animal an " +
            "WHERE an.id = :id")
    AnimalProjection getAnimalProjectionById(@Param("id") Long id);

    @Query("SELECT an " +
            "FROM Animal an " +
            "WHERE an.id in :ids")
    List<AnimalProjection> getAllById(@Param("ids") List<Long> listId);

    Optional<Animal> findByChippingLocationId(Location location);


}
