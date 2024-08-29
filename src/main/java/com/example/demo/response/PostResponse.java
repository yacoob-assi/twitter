package com.example.demo.response;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import com.example.demo.models.Post;
import com.example.demo.models.User;

import jakarta.persistence.OneToMany;
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
    
    private String tag;

    private int usersLiked;
    
    private List<User>likes= new ArrayList<>();
    
    private List<User> usersRepost=new ArrayList();

    private Post parent;

    private List<Post> comments = new ArrayList<>();
   
    private int statisics;

    private String image;

   
}
