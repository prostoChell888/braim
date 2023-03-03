package com.example.if_else.utils.SerchingParametrs;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AcountSerchParametrs {
    private  String firstName;
    private  String lastName;
    @Email
    private String email;
    private Integer from = 0;
    private Integer size = 10;

    public void setFirstName(String firstName) {
        this.firstName = firstName.toLowerCase();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email.toUpperCase();
    }
}
