package com.example.airBnbProject.dto;

import com.example.airBnbProject.entity.User;
import com.example.airBnbProject.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
