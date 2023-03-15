package com.example.if_else.Reposiories;

import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.mapers.VisitsLocationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VisitLocationRepository
        extends JpaRepository<VisitsLocation, Long>,
        JpaSpecificationExecutor<VisitsLocationProjection> {

    @Query("SELECT vl.id as id, " +
            "vl.dateTimeOfVisitLocationPoint as dateTimeOfVisitLocationPoint, " +
            "vl.locationPointId as locationInfo " +
            "FROM VisitsLocation vl " +
            " where vl.id = :id")
    VisitsLocationProjection getVisitsLocationById(@Param("id") Long id);



    @Query("SELECT visit.id as id,  " +
            "visit.dateTimeOfVisitLocationPoint as dateTimeOfVisitLocationPoint, " +
            "visit.locationPointId as locationInfo " +
            "FROM VisitsLocation visit  " +
            "WHERE :animalId = visit.animal.id " +
            "AND (CAST(:startDateTime  as timestamp ) is null or visit.dateTimeOfVisitLocationPoint >= :startDateTime) " +
            "AND (CAST(:endDateTime  as timestamp ) is null or visit.dateTimeOfVisitLocationPoint <= :endDateTime) ")
    Page<VisitsLocationProjection> findLocatiomByParam(@Param("animalId") Long animalId,
                                                       @Param("startDateTime")Date startDateTime,
                                                       @Param("endDateTime")Date endDateTime,
                                                       Pageable pageable);
}
