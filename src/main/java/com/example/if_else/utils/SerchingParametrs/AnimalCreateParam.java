package com.example.if_else.utils.SerchingParametrs;

import com.example.if_else.enums.Gender;
import com.example.if_else.enums.LifeStatus;
import com.example.if_else.validation.anotation.ValidElOfList;
import com.example.if_else.validation.anotation.ValidFloat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class AnimalCreateParam {


    @NotNull
    @Length(min = 1)
    @ValidElOfList
    private List<Long> animalTypes;

    @ValidFloat
    private Float weight;

    @ValidFloat
    private Float length;

    @ValidFloat
    private Float height;

    @NotNull
    private Gender gender;

    @NotNull
    private LifeStatus lifeStatus;

    @Min(1)
    @NotNull
    private Integer chipperId;

    @Min(1)
    @NotNull
    private Long chippingLocationId;
}
