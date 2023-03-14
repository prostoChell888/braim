package com.example.if_else.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LocationUpdateRequest {
    @Min(1) @NotNull Long visitedLocationPointId;
    @Min(1) @NotNull Long locationPointId;
}
