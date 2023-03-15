package com.example.if_else.Reposiories;

import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.mapers.VisitsLocationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VisitLocationRepository extends JpaRepository<VisitsLocation, Long>,
        JpaSpecificationExecutor<VisitsLocationProjection> {

    @Query("SELECT visit " +
            "FROM Animal animal " +
            "JOIN animal.visitedLocations visit  " +
            "WHERE :animalId = animal.id " +
            "AND (CAST(:startDateTime  as timestamp ) is null or visit.dateTimeOfVisitLocationPoint >= :startDateTime) " +
            "AND (CAST(:endDateTime  as timestamp ) is null or visit.dateTimeOfVisitLocationPoint <= :endDateTime) ")
    Page<VisitsLocationProjection> findLocatiombyParam(Long animalId, Date startDateTime, Date endDateTime,
                                                       Pageable pageable);
}
