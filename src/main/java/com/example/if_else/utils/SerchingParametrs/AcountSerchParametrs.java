package com.example.if_else.utils.SerchingParametrs;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AcountSerchParametrs {
    private  String firstName;
    private  String lastName;
    private String email;
    private Integer from = 0;
    private Integer size = 10;
}
