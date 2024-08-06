package com.example.demo.response;

import java.sql.Date;

import com.example.demo.response.UserRespone.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostResponse {

    private int postId;

    private Date date;

    private int repostNum;

    private String text;

    private UserResponse user;

    private int usersLiked;

}
