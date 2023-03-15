package com.example.if_else.Reposiories;

import com.example.if_else.Models.VisitsLocation;
import com.example.if_else.mapers.VisitsLocationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitsLocationRepository
        extends JpaRepository<VisitsLocation, Long>,
        JpaSpecificationExecutor<VisitsLocationProjection> {

    VisitsLocationProjection getVisitsLocationById(Long id);


}
