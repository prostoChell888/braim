package com.example.if_else.utils.SerchingParametrs;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AmimalSerchParameters {
    private Long animalId;
    private Date startDateTime;
    private Date endDateTime;
    private Long chippingLocationId;
    private String lifeStatus;
    private String gender;
    private Integer from = 0;
    private Integer size = 10;
}
