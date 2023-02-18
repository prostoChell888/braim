package com.example.if_else.Reposiories;

import com.example.if_else.Models.VisitsLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitsLocationRepository
        extends JpaRepository<VisitsLocation, Long> {


}
