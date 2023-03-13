package com.example.if_else.utils.SerchingParametrs;

import com.example.if_else.enums.Gender;
import com.example.if_else.enums.LifeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmimalSerchParameters {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmX")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmX")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    @Min(value = 1)
    private Long chipperId;

    @Min(value = 1)
    private Long chippingLocationId;

    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Min(value = 0)
    private Integer from = 0;

    @Min(value = 1)
    private Integer size = 10;
}
