package com.example.if_else.mapers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@JsonPropertyOrder({ "id", "dateTimeOfVisitLocationPoint","locationPointId"})
public interface VisitsLocationProjection {
    Long getId();

    @Value("#{target.locationInfo.id}")
    Long getLocationPointId();

    Date getDateTimeOfVisitLocationPoint();

}
