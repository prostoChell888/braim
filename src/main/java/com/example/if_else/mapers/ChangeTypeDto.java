package com.example.if_else.mapers;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
public class ChangeTypeDto {
    Long oldTypeId;
    Long newTypeId;
}
