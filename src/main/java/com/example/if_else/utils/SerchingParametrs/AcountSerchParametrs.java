package com.example.if_else.utils.SerchingParametrs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcountSerchParametrs {
    private  String firstName;
    private  String lastName;
    private String email;
    private Integer from = 0;
    private Integer size = 10;
}
