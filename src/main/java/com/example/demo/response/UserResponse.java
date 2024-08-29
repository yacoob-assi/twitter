package com.example.demo.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer userId;

    private Integer age;

    private String name;

    private String tag;

    private Date birthDate;

    private String password;

    private String email;

    private int followers;

    private int followings;
    private String image;
}
