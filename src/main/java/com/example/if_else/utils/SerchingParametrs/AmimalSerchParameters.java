package com.example.if_else.utils.SerchingParametrs;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmimalSerchParameters {
    public Date startDateTime;
    public Date endDateTime;
    public Long chippingLocationId;
    public String lifeStatus;
    public String gender;
    public Integer from;
    public Integer size;
}
